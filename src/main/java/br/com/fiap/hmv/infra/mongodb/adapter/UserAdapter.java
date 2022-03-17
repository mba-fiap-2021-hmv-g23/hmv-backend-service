package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.UserPort;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.mongodb.entity.UserEntity;
import br.com.fiap.hmv.infra.mongodb.mapper.UserEntityMapper;
import br.com.fiap.hmv.infra.mongodb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.mongodb.mapper.UserEntityMapper.toUserEntity;
import static java.util.UUID.randomUUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserAdapter implements UserPort {

    private final ReactiveMongoOperations mongoOperations;
    private final UserRepository userRepository;

    @Override
    public Mono<Void> insert(final User user) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do usuário na base de dados.");
        user.setUserId(randomUUID().toString());
        return userRepository.insert(toUserEntity(user)).then();
    }

    @Override
    public Mono<Void> updateSession(final User user) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando atualização da sessão do usuário.");
        return mongoOperations.updateFirst(
                new Query(new Criteria("_id").is(user.getUserId())),
                new Update().set("accessTokenExpiresIn", user.getAccessTokenExpiresIn())
                        .set("refreshTokenExpiresIn", user.getRefreshTokenExpiresIn())
                        .set("refreshToken", user.getRefreshToken()),
                UserEntity.class
        ).then();
    }

    @Override
    public Mono<User> findByLogin(String username, String taxId) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando consulta de usuário na base de dados por nome de usuário ou CPF.");
        return userRepository.findByUsernameOrTaxId(username, taxId).map(UserEntityMapper::toUser);
    }

    @Override
    public Mono<User> findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken).map(UserEntityMapper::toUser);
    }
}