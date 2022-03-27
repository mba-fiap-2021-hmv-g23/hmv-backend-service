package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel("DeleteCheckInCancelRequestV1")
@Getter
public class DeleteCheckInCancelRequest {

    @ApiModelProperty(value = "Motivo do cancelamento.", required = true)
    private String reason;

}