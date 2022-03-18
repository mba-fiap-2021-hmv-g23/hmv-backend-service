package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("PostPatientResponseV1")
@Getter
@Builder
public class PostPatientResponse {

    @ApiModelProperty(value = "ID do paciente.", required = true)
    private String patientId;

}