package br.com.fiap.hmv.infra.mongodb.repository;

import br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AttendanceServiceRepository extends ReactiveMongoRepository<AttendanceServiceEntity, String> {

}