package br.com.fiap.hmv.infra.mongodb.repository;

import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CheckInRepository extends ReactiveMongoRepository<CheckInEntity, String> {

    Flux<CheckInEntity> findByServiceStartTimeIsNull();

    Mono<CheckInEntity> findOneByPatientTaxIdAndServiceStartTimeIsNull(String patientTaxId);

}