package br.com.fiap.hmv.infra.rest.api;

import br.com.fiap.hmv.application.service.PatientAppService;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.mapper.PatientApiModelMapper;
import br.com.fiap.hmv.infra.rest.api.model.PostLoginResponse;
import br.com.fiap.hmv.infra.rest.api.model.PostPatientRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostPatientResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.rest.api.mapper.PatientApiModelMapper.toPatient;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Paciente")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/patient")
public class PatientApi {

    private final PatientAppService appService;

    @ApiOperation(value = "Cadastrar Paciente", response = PostLoginResponse.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<PostPatientResponse> post(
            @ApiParam("Dados para cadastrar um paciente.")
            @RequestBody PostPatientRequest request
    ) {
        log.info("[INFRA_REST_API POST /patient] Iniciando chamada ao app service para cadastrar um paciente.");
        Patient patient = toPatient(request);
        return appService.insert(patient).thenReturn(patient).map(PatientApiModelMapper::toPostPatientResponse)
                .doOnSuccess(u -> log.info("[INFRA_REST_API POST /patient] Finalizado com sucesso."))
                .doOnError(t -> log.error("[INFRA_REST_API POST /patient] Finalizado com erro [{}].",
                        t.getClass().getSimpleName()
                ));
    }
}
