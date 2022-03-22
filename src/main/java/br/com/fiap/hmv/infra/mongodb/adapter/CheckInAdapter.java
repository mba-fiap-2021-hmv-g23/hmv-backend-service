package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.mongodb.repository.CheckInRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

import static br.com.fiap.hmv.infra.mongodb.mapper.CheckInEntityMapper.toCheckInEntity;
import static java.time.Duration.ofSeconds;
import static java.time.LocalDateTime.now;
import static java.util.Locale.ROOT;
import static org.apache.commons.lang3.RandomStringUtils.random;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckInAdapter implements CheckInPort {

    private final CheckInRepository checkInRepository;

    @Override
    public Mono<Void> insert(CheckIn checkIn) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do check-in na base de dados.");
        String beginId = random(4, true, true).toUpperCase(ROOT);
        String beginEnd = random(3, false, true).toUpperCase(ROOT);
        checkIn.setCheckInId(beginId + "-" + beginEnd);
        checkIn.setInclusionDate(now());
        return checkInRepository.insert(toCheckInEntity(checkIn)).onErrorResume(t -> {
            log.error("[INFRA_MONGODB_ADAPTER] Erro de inclusão do check-in. [{}].", t.getLocalizedMessage());
            return insert(checkIn).then(Mono.empty());
        }).timeout(ofSeconds(5)).then();
    }

    @Override
    public Mono<Void> update(CheckIn checkIn) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando atualização do check-in na base de dados.");
        return checkInRepository.save(toCheckInEntity(checkIn)).then();
    }

    @Override
    public Mono<List<CheckIn>> findAwaitingAttendance() {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando busca dos check-ins aguardando o atendimento na base de dados.");
        return Mono.just(List.of());
    }

}