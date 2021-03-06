package br.com.fiap.hmv.infra.rest.api.v1;

import br.com.fiap.hmv.application.service.CheckInAppService;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.rest.api.v1.mapper.CheckInModelMapper;
import br.com.fiap.hmv.infra.rest.api.v1.model.DeleteCheckInCancelRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetCheckInFormResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetCheckInResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PutCheckInResponse;
import br.com.fiap.hmv.security.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.CheckInModelMapper.toCheckIn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
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
            @PathVariable String checkInId
    ) {
        log.info("[INFRA_REST_API PUT /v1/check-in/{checkInId}/confirm] Iniciando chamada ao app service para confirmar " +
                "o check-in do paciente.");
        return appService.confirm(checkInId).map(CheckInModelMapper::toPutCheckInResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API PUT /v1/check-in/{checkInId}/confirm] " +
                        "Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API PUT /v1/check-in/{checkInId}/confirm] " +
                                "Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

    @ApiOperation(value = "Cancelar Check-in", response = PutCheckInResponse.class)
    @DeleteMapping(path = "/{checkInId}/cancel", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> putCancel(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "ID do Check-In.", required = true)
            @PathVariable String checkInId,
            @ApiParam(value = "Dados para cancelar Check-In.", required = true)
            @RequestBody DeleteCheckInCancelRequest request
    ) {
        log.info("[INFRA_REST_API DELETE /v1/check-in/{checkInId}/confirm] Iniciando chamada ao app service para " +
                "cancelar o check-in do paciente.");
        CheckIn checkIn = toCheckIn(checkInId, request);
        return appService.cancel(checkIn)
                .doOnSuccess(u -> log.info("[INFRA_REST_API DELETE /v1/check-in/{checkInId}/cancel] " +
                        "Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API DELETE /v1/check-in/{checkInId}/cancel] " +
                                "Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

    @ApiOperation(value = "Obter dados completo do Check-in", response = PutCheckInResponse.class)
    @GetMapping(path = "/{checkInId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<GetCheckInResponse> get(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "ID do Check-In.", required = true)
            @PathVariable String checkInId
    ) {
        log.info("[INFRA_REST_API GET /v1/check-in/{checkInId}] Iniciando chamada ao app service para confirmar " +
                "o check-in do paciente.");
        return appService.findById(checkInId).map(CheckInModelMapper::toGetCheckInResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API GET /v1/check-in/{checkInId}] " +
                        "Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API GET /v1/check-in/{checkInId}] " +
                                "Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

    @ApiOperation(value = "Obter formul??rio de Check-In", response = GetCheckInFormResponse[].class)
    @GetMapping(path = "/form", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Flux<GetCheckInFormResponse> putConfirm(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        log.info("[INFRA_REST_API /v1/check-in/form] Iniciando chamada ao app service para obter " +
                "formul??rio de check-in.");
        return appService.getForm(jwtService.getPatientId(accessToken))
                .map(CheckInModelMapper::toGetCheckInFormResponse)
                .doOnComplete(() -> log.info("[INFRA_REST_API POST /v1/check-in/form] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/check-in/form] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

}