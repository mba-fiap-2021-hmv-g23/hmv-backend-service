package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInCallServiceEntity;
import br.com.fiap.hmv.infra.mongodb.repository.AttendanceServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity.TTL_MINUTES;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.UUID.randomUUID;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttendanceAdapter implements AttendancePort {

    private final ReactiveMongoOperations mongoOperations;
    private final AttendanceServiceRepository attendanceServiceRepository;

    @Override
    public Mono<Void> startServiceToPatient(String userTaxId) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do usuário em serviço de atendimento na base de dados.");
        return stopServicesByUserTaxId(userTaxId)
                .switchIfEmpty(Mono.defer(() -> attendanceServiceRepository.insert(AttendanceServiceEntity.builder()
                        .attendanceServiceId(randomUUID().toString())
                        .userTaxId(userTaxId)
                        .startDate(now())
                        .build()
                ).then()));
    }

    @Override
    public Mono<Void> stopServiceToPatient(String userTaxId) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando atualização de encerramento de serviço de atendimento na base de dados.");
        return stopServicesByUserTaxId(userTaxId);
    }

    @Override
    public Flux<User> findAttendantsInService() {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando busca dos usuários em serviço de atendimento base de dados.");
        return Flux.empty();
    }

    @Override
    public Mono<Void> insertCallToStartAttendance(CheckIn checkIn) {
        return mongoOperations.findAndModify(
                        query(where("_id").is(checkIn.getCheckInId())),
                        new Update()
                                .setOnInsert("patientId", checkIn.getPatient().getPatientId())
                                .setOnInsert("inclusionDate", now())
                                .setOnInsert("noShows", 0)
                                .setOnInsert("ttl", now().plusHours(48))
                                .set("userId", checkIn.getAttendant().getUserId())
                                .inc("calls", 1L),
                        options().returnNew(true).upsert(true),
                        CheckInCallServiceEntity.class)
                .doOnSuccess(checkInCallServiceEntity -> {
                    checkIn.setCalls(checkInCallServiceEntity.getCalls());
                    checkIn.setNoShows(checkInCallServiceEntity.getNoShows());
                }).then();
    }

    private Mono<Void> stopServicesByUserTaxId(String userTaxId) {
        return mongoOperations.updateMulti(
                new Query(new Criteria("userTaxId").is(userTaxId).and("endDate").isNull()),
                new Update().set("endDate", now().plus(TTL_MINUTES, MINUTES)),
                AttendanceServiceEntity.class
        ).then();
    }

}