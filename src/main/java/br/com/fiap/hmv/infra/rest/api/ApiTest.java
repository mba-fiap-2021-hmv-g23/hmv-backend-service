package br.com.fiap.hmv.infra.rest.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.OK;

@Api(hidden = true)
@Slf4j
@RequiredArgsConstructor
@RestController("testApiV1")
@RequestMapping("/v1/tests")
public class ApiTest {

    @ApiOperation(value = "Teste", hidden = true)
    @GetMapping
    @ResponseStatus(OK)
    public Mono<String> get() {
        log.info("[INFRA_REST_API GET /v1/test] Iniciando chamada de teste.");
        return Mono.just("Teste realizado com sucesso.");
    }
}
