package br.com.fiap.hmv.infra.rest.api.v1;

import br.com.fiap.hmv.application.service.AttendanceAppService;
import br.com.fiap.hmv.infra.rest.api.v1.mapper.AttendanceModelMapper;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceNextPatientResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceQueueCallsResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostStartServiceRequest;
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
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Atendimento")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/attendances")
public class AttendanceApi {

    private final AttendanceAppService appService;
    private final JwtService jwtService;

    @ApiOperation(value = "Iniciar jornada de serviço à pacientes.")
    @PostMapping(path = "/services")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> postStartService(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "Dados para iniciar serviço no balcão de atendimentos.")
            @RequestBody PostStartServiceRequest request
    ) {
        log.info("[INFRA_REST_API POST /v1/attendances/services] Iniciando chamada ao app service para " +
                "inserir jornada de serviço à pacientes."
        );
        return appService.startAttendanceService(request.getServiceDesk(), jwtService.getUserId(accessToken))
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/attendances/services] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/attendances/services] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));

    }

    @ApiOperation(value = "Encerrar jornada de serviço à pacientes.")
    @DeleteMapping(path = "/services")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> deleteStartService(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        log.info("[INFRA_REST_API DELETE /v1/attendances/services] Iniciando chamada ao app service para " +
                "remover jornada de serviço à pacientes."
        );
        return appService.stopAttendanceService(jwtService.getUserId(accessToken))
                .doOnSuccess(u -> log.info("[INFRA_REST_API DELETE /v1/attendances/services] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API DELETE /v1/attendances/services] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @ApiOperation(
            value = "Iniciar chamada ao próximo paciente aguardando atendimento.",
            response = GetAttendanceNextPatientResponse.class
    )
    @GetMapping(path = "/next-patient")
    @ResponseStatus(OK)
    public Mono<GetAttendanceNextPatientResponse> getNextPatient(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        log.info("[INFRA_REST_API GET /v1/attendances/next-patient] Iniciando chamada ao app service para " +
                "iniciar chamada ao próximo paciente aguardando atendimento."
        );
        return appService.nextPatientToAttendance(jwtService.getUserId(accessToken))
                .map(AttendanceModelMapper::toGetAttendanceNextPatientResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API GET /v1/attendances/next-patient] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API GET /v1/attendances/next-patient] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @ApiOperation(
            value = "Buscar fila de chamadas à pacientes.",
            response = GetAttendanceNextPatientResponse.class
    )
    @GetMapping(path = "/queue-calls")
    @ResponseStatus(OK)
    public Mono<GetAttendanceQueueCallsResponse> getQueueCalls() {
        log.info("[INFRA_REST_API GET /v1/attendances/queue-calls] Iniciando chamada ao app service buscar fila de " +
                "pacientes aguardando atendimento."
        );
        return appService.findQueueCalls()
                .map(AttendanceModelMapper::toGetAttendanceQueueCallsResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API GET /v1/attendances/next-patient] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API GET /v1/attendances/next-patient] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @ApiOperation(value = "Iniciar atendimento ao paciente")
    @PutMapping(path = "/{checkInId}/start")
    @ResponseStatus(OK)
    public Mono<Void> putStartAttendance(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken,
            @ApiParam(value = "ID do Check-In.", required = true)
            @PathVariable String checkInId
    ) {
        log.info("[INFRA_REST_API PUT /v1/attendances/{checkInId}/start] Iniciando chamada ao app service " +
                "para iniciar atendimento ao paciente.");
        return appService.startAttendanceToPatient(checkInId, jwtService.getUserId(accessToken))
                .doOnSuccess(u -> log.info("[INFRA_REST_API PUT /v1/attendances/{checkInId}/start] " +
                        "Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API PUT /v1/attendances/{checkInId}/start] " +
                                "Finalizado com erro [{}].",
                        t.getClass().getSimpleName()));
    }

}