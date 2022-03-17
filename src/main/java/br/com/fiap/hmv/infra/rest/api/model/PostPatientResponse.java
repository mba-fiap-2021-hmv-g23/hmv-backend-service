package br.com.fiap.hmv.infra.rest.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("PostPatientResponse")
@Getter
@Builder
public class PostPatientResponse {

    @ApiModelProperty(value = "ID do paciente.", required = true)
    private String patientId;

}