package br.com.fiap.hmv.infra.rest.api.v1;

import br.com.fiap.hmv.application.service.PatientAppService;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.v1.mapper.PatientApiModelMapper;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetPatientResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostPatientRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostPatientResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.PatientApiModelMapper.toPatient;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Paciente")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/patients")
public class PatientApi {

    private final PatientAppService appService;

    @ApiOperation(value = "Cadastrar Paciente", response = PostPatientResponse.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<PostPatientResponse> post(
            @ApiParam("Dados para cadastrar um paciente.")
            @RequestBody PostPatientRequest request
    ) {
        log.info("[INFRA_REST_API POST /v1/patients] Iniciando chamada ao app service para cadastrar um paciente.");
        Patient patient = toPatient(request);
        return appService.insert(patient).thenReturn(patient).map(PatientApiModelMapper::toPostPatientResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /v1/patients] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /v1/patients] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @ApiOperation(value = "Obter Paciente", response = GetPatientResponse.class)
    @GetMapping(path = "/{patientId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<GetPatientResponse> get(
            @ApiParam(value = "ID do paciente.", required = true)
            @PathVariable String patientId
    ) {
        log.info("[INFRA_REST_API GET /v1/patients/{patientId}] Iniciando chamada ao app service para obter um paciente.");
        return appService.search(patientId).map(PatientApiModelMapper::toGetPatientResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API GET /v1/patients/{patientId}] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API GET /v1/patients/{patientId}] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

    @ApiOperation(value = "Buscar Pacientes", response = GetPatientResponse.class)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Flux<GetPatientResponse> get() {
        log.info("[INFRA_REST_API GET /v1/patients] Iniciando chamada ao app service para buscar pacientes.");
        return appService.search().map(PatientApiModelMapper::toGetPatientResponse)
                .doOnComplete(() -> log.info("[INFRA_REST_API GET /v1/patients] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API GET /v1/patients] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }

}