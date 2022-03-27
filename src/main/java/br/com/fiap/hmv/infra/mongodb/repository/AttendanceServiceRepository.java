package br.com.fiap.hmv.infra.mongodb.repository;

import br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AttendanceServiceRepository extends ReactiveMongoRepository<AttendanceServiceEntity, String> {

    Mono<Void> deleteByAttendantId(String attendantId);

}