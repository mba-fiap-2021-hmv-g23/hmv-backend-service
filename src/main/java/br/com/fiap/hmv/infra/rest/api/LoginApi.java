package br.com.fiap.hmv.infra.rest.api;

import br.com.fiap.hmv.application.service.AuthenticationAppService;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.rest.api.mapper.UserApiModelMapper;
import br.com.fiap.hmv.infra.rest.api.model.PostLoginRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostLoginResponse;
import br.com.fiap.hmv.infra.rest.api.model.PostRefreshTokenRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostRefreshTokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = "Login")
@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginApi {

    private final AuthenticationAppService appService;

    @ApiOperation(value = "Realizar Login", response = PostLoginResponse.class)
    @PostMapping(path = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<PostLoginResponse> post(
            @ApiParam("Dados para realizar o login.")
            @RequestBody PostLoginRequest request
    ) {
        log.info("[INFRA_REST_API POST /login] Iniciando chamada ao app service para realizar login.");
        User user = toUser(request);
        return appService.login(user).thenReturn(user).map(UserApiModelMapper::toPostLoginResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /login] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /login] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @ApiOperation(value = "Renovar Sessão", response = PostRefreshTokenResponse.class)
    @PostMapping(path = "/refresh-token", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<PostRefreshTokenResponse> refreshToken(
            @ApiParam("Dados para renovar login.")
            @RequestBody PostRefreshTokenRequest request
    ) {
        log.info("[INFRA_REST_API POST /refresh-token] Iniciando chamada ao app service para renovar sessão.");
        User user = toUser(request);
        return appService.refreshToken(user).thenReturn(user).map(UserApiModelMapper::toPostRefreshTokenResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /refresh-token] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /refresh-token] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

}