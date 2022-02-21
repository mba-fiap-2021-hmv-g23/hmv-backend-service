package br.com.fiap.hmv.infra.rest.api;

import br.com.fiap.hmv.application.service.UserAppService;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.rest.api.mapper.UserApiModelMapper;
import br.com.fiap.hmv.infra.rest.api.model.PostUserRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.rest.api.mapper.UserApiModelMapper.toUser;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController("userApiV1")
@RequestMapping("/v1/users")
public class UserApi {

    private final UserAppService appService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<PostUserResponse> post(@RequestBody PostUserRequest request) {
        log.info("[INFRA_REST_API POST /v1/users] Iniciando chamada para cadastrar usuário.");
        User user = toUser(request);
        return appService.insert(user).thenReturn(user)
                .map(UserApiModelMapper::toPostUserResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/users] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/users] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

}