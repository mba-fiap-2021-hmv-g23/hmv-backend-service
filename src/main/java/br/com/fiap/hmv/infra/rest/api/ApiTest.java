package br.com.fiap.hmv.infra.rest.api;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController("testApiV1")
@RequestMapping("/v1/tests")
public class ApiTest {

    @ApiOperation(value = "Teste Todos", hidden = true)
    @GetMapping
    @ResponseStatus(OK)
    public Mono<String> getTodos() {
        log.info("[INFRA_REST_API GET /v1/test] Iniciando chamada de todos.");
        return Mono.just("Teste TODOS realizado com sucesso.");
    }

}