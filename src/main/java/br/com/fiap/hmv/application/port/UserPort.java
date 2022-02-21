package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserPort {

    Mono<Void> insert(final User user);

    Mono<Void> updateSession(final User user);

    Mono<User> findByLogin(String username, String taxId);

    Mono<User> findByRefreshToken(String refreshToken);

}