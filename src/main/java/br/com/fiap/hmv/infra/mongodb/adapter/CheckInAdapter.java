package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.exception.HttpException;
import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;
import br.com.fiap.hmv.infra.mongodb.mapper.CheckInEntityMapper;
import br.com.fiap.hmv.infra.mongodb.repository.CheckInRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.mongodb.mapper.CheckInEntityMapper.toCheckInEntity;
import static java.time.Duration.ofSeconds;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.util.Comparator.comparing;
import static java.util.Locale.ROOT;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckInAdapter implements CheckInPort {

    private final ReactiveMongoOperations mongoOperations;
    private final CheckInRepository checkInRepository;

    @Override
    public Mono<Void> insert(CheckIn checkIn) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do check-in na base de dados.");
        return checkInRepository.findByPatientIdAndServiceStartDateIsNull(checkIn.getPatient().getPatientId())
                .flatMap(u -> Mono.error(new HttpException("Já existe um check-in em andamento. " +
                        "Aguarde que em breve você será chamado para atendimento.", BAD_REQUEST)))
                .switchIfEmpty(Mono.defer(() -> {
                    String beginId = random(2, true, false).toUpperCase(ROOT);
                    String middleId = random(2, false, true).toUpperCase(ROOT);
                    String endId = random(1, true, false).toUpperCase(ROOT);
                    checkIn.setExpiresDate(now().plus(4, HOURS));
                    checkIn.setCheckInId(beginId + "-" + middleId + endId);
                    return checkInRepository.insert(toCheckInEntity(checkIn)).onErrorResume(t -> {
                        log.error("[INFRA_MONGODB_ADAPTER] Erro de inclusão do check-in. [{}].",
                                t.getLocalizedMessage());
                        return insert(checkIn).then(Mono.empty());
                    }).timeout(ofSeconds(5));
                })).then();
    }

    @Override
    public Mono<Void> update(CheckIn checkIn) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando atualização do check-in na base de dados.");
        return checkInRepository.save(toCheckInEntity(checkIn)).then();
    }

    @Override
    public Mono<CheckIn> getById(String checkInId) {
        return checkInRepository.findById(checkInId).map(CheckInEntityMapper::toCheckIn);
    }

    @Override
    public Flux<CheckIn> findAwaitingAttendance() {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando busca dos check-ins aguardando o atendimento na base de dados.");
        return mongoOperations.find(query(where("serviceStartDate").isNull()), CheckInEntity.class)
                .map(CheckInEntityMapper::toCheckIn)
                .sort(comparing(CheckIn::getServiceStartBaseDate));
    }

}