package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.exception.UnauthorizedException;
import br.com.fiap.hmv.application.port.UserPort;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static br.com.fiap.hmv.application.utils.ObfuscateUtils.obfuscateTaxId;
import static br.com.fiap.hmv.application.utils.ObfuscateUtils.obfuscateToken;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationAppService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserPort userPort;

    @Value("${auth.accessTokenExpiresInMinutes}")
    private Integer accessTokenExpiresInMinutes;

    @Value("${auth.refreshTokenExpiresInMinutes}")
    private Integer refreshTokenExpiresInMinutes;

    public Mono<Void> login(final User user) {
        log.info("[APPLICATION_SERVICE] Iniciando o login. Nome de usuário: {}. CPF: {}",
                user.getUsername(), obfuscateTaxId(user.getTaxId())
        );
        return userPort.findByLogin(user.getUsername(), user.getTaxId())
                .filter(userStorage -> passwordEncoder.matches(user.getPassword(), userStorage.getPassword()))
                .switchIfEmpty(Mono.error(new UnauthorizedException("Dados inválidos.")))
                .doOnSuccess(userStorage -> {
                    updateAccessToken(user, userStorage);
                    log.info("[APPLICATION_SERVICE] Usuário logado com sucesso. Nome de usuário: {}, Token de acesso: {}",
                            user.getUsername(), obfuscateToken(user.getAccessToken())
                    );
                })
                .then();
    }

    public Mono<Void> refreshToken(final User user) {
        log.info("[APPLICATION_SERVICE] Iniciando a renovação do login do usuário. Token de renovação: {}.",
                obfuscateToken(user.getRefreshToken())
        );
        return userPort.findByRefreshToken(user.getRefreshToken())
                .filter(userStorage -> nonNull(userStorage.getRefreshTokenExpiresIn())
                        && userStorage.getRefreshTokenExpiresIn().isAfter(now())
                )
                .switchIfEmpty(Mono.error(new UnauthorizedException("Dados inválidos.")))
                .doOnSuccess(userStorage -> {
                    updateAccessToken(user, userStorage);
                    log.info("[APPLICATION_SERVICE] Login renovado com sucesso. Nome de usuário: {}, " +
                                    "Novo Token de acesso: {}",
                            user.getUsername(), obfuscateToken(user.getAccessToken())
                    );
                })
                .then();
    }

    private void updateAccessToken(final User user, User userStorage) {
        LocalDateTime now = now().truncatedTo(SECONDS);
        if (Objects.isNull(userStorage.getAccessTokenExpiresIn()) || now().isAfter(userStorage.getAccessTokenExpiresIn())) {
            user.setAccessTokenExpiresIn(now.plus(accessTokenExpiresInMinutes, MINUTES));
            user.setRefreshTokenExpiresIn(now.plus(refreshTokenExpiresInMinutes, MINUTES));
        } else {
            user.setAccessTokenExpiresIn(userStorage.getAccessTokenExpiresIn());
            user.setRefreshTokenExpiresIn(userStorage.getRefreshTokenExpiresIn());
        }
        user.setId(userStorage.getId());
        user.setTaxId(userStorage.getTaxId());
        user.setUsername(userStorage.getUsername());
        user.setFullName(userStorage.getFullName());
        user.setRefreshToken(UUID.randomUUID().toString());
        user.setAccessToken(jwtService.generateAccessToken(user));
        userPort.updateSession(user).subscribe();
    }

}