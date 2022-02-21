package br.com.fiap.hmv.infra.rest.api;

import br.com.fiap.hmv.application.service.AuthenticationAppService;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.rest.api.mapper.UserApiModelMapper;
import br.com.fiap.hmv.infra.rest.api.model.PostLoginRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostLoginResponse;
import br.com.fiap.hmv.infra.rest.api.model.PostRefreshTokenRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostRefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.rest.api.mapper.UserApiModelMapper.toUser;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController("loginApiV1")
public class LoginApi {

    private final AuthenticationAppService appService;

    @PostMapping(path = "/v1/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<PostLoginResponse> post(@RequestBody PostLoginRequest request) {
        log.info("[INFRA_REST_API POST /v1/login] Iniciando login.");
        User user = toUser(request);
        return appService.login(user).thenReturn(user).map(UserApiModelMapper::toPostLoginResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/login] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/login] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @PostMapping(path = "/v1/refresh-token", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<PostRefreshTokenResponse> refreshToken(@RequestBody PostRefreshTokenRequest request) {
        log.info("[INFRA_REST_API POST /v1/login] Iniciando refresh-token.");
        User user = toUser(request);
        return appService.refreshToken(user).thenReturn(user).map(UserApiModelMapper::toPostRefreshTokenResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/refresh-token] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/refresh-token] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

}