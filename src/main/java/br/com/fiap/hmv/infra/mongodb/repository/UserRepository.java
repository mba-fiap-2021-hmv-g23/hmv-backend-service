package br.com.fiap.hmv.infra.mongodb.repository;

import br.com.fiap.hmv.infra.mongodb.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, UUID> {

    Mono<UserEntity> findByUsernameOrTaxId(String username, String taxId);

    Mono<UserEntity> findByRefreshToken(String refreshToken);

}