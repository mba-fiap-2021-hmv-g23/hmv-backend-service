package br.com.fiap.hmv.infra.rest.api.v1;

import br.com.fiap.hmv.application.service.AttendanceAppService;
import br.com.fiap.hmv.security.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NO_CONTENT;

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
            @RequestHeader("Authorization") String accessToken
    ) {
        log.info("[INFRA_REST_API POST /v1/attendances/services] Iniciando chamada ao app service para " +
                "inserir jornada de serviço à pacientes."
        );
        return appService.startServiceToPatient(jwtService.getTaxId(accessToken));
    }

    @ApiOperation(value = "Encerrar jornada de serviço à pacientes.")
    @DeleteMapping(path = "/services")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> deleteStartService(
            @ApiParam(value = "Token de acesso.", required = true)
            @RequestHeader("Authorization") String accessToken
    ) {
        log.info("[INFRA_REST_API POST /v1/attendances/services] Iniciando chamada ao app service para " +
                "remover jornada de serviço à pacientes."
        );
        return appService.stopServiceToPatient(jwtService.getTaxId(accessToken));
    }

}