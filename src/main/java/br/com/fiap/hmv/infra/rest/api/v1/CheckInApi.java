package br.com.fiap.hmv.infra.rest.api.v1;

import br.com.fiap.hmv.application.service.CheckInAppService;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.rest.api.v1.mapper.CheckInModelMapper;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PutCheckInResponse;
import br.com.fiap.hmv.security.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.CheckInModelMapper.toCheckIn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Check-In")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/check-in")
public class CheckInApi {

    private final CheckInAppService appService;
    private final JwtService jwtService;

    @ApiOperation(value = "Realizar Check-In", response = PostCheckInResponse.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<PostCheckInResponse> post(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam("Dados para realizar o check-in.")
            @RequestBody PostCheckInRequest request
    ) {
        log.info("[INFRA_REST_API POST /v1/check-in] Iniciando chamada ao app service para realizar o check-in " +
                "do paciente.");
        CheckIn checkIn = toCheckIn(jwtService.getPatientId(accessToken), request);
        return appService.checkIn(checkIn).thenReturn(checkIn).map(CheckInModelMapper::toPostCheckInResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/check-in] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/check-in] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

    @ApiOperation(value = "Confirmar Check-in", response = PutCheckInResponse.class)
    @PutMapping(path = "/{checkInId}/confirm", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<PutCheckInResponse> putConfirm(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "ID do Check-In.", required = true)
            @PathVariable() String checkInId
    ) {
        log.info("[INFRA_REST_API /v1/check-in/{checkInId}/confirm] Iniciando chamada ao app service para confirmar " +
                "o check-in do paciente.");
        return appService.confirm(checkInId).map(CheckInModelMapper::toPutCheckInResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/check-in/{checkInId}/confirm] " +
                        "Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/check-in/{checkInId}/confirm] " +
                                "Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

}