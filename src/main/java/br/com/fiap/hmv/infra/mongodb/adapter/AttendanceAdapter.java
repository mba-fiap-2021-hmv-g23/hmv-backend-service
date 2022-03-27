package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity;
import br.com.fiap.hmv.infra.mongodb.repository.AttendanceServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity.TTL_MINUTES;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.UUID.randomUUID;

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

    private Mono<Void> stopServicesByUserTaxId(String userTaxId) {
        return mongoOperations.updateMulti(
                new Query(new Criteria("userTaxId").is(userTaxId).and("endDate").isNull()),
                new Update().set("endDate", now().plus(TTL_MINUTES, MINUTES)),
                AttendanceServiceEntity.class
        ).then();
    }

}