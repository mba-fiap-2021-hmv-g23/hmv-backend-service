package br.com.fiap.hmv.infra.mongodb.repository;

import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CheckInRepository extends ReactiveMongoRepository<CheckInEntity, String> {

}
