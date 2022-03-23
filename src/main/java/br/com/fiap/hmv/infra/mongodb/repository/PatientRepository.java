package br.com.fiap.hmv.infra.mongodb.repository;

import br.com.fiap.hmv.infra.mongodb.entity.PatientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PatientRepository extends ReactiveMongoRepository<PatientEntity, String> {

    Mono<PatientEntity> findByTaxId(String taxId);

}