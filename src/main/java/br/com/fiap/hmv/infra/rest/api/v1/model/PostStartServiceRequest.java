package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel("PostStartServiceRequestV1")
@Getter
public class PostStartServiceRequest {

    @ApiModelProperty(value = "Balcão de atendimento.", required = true)
    private String serviceDesk;

}