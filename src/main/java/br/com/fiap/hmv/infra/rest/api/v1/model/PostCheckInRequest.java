package br.com.fiap.hmv.infra.rest.api.v1.model;

import br.com.fiap.hmv.domain.entity.CheckInMode;
import br.com.fiap.hmv.domain.entity.EstimatedTimeArrival;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "PostCheckInRequestV1")
@Getter
public class PostCheckInRequest {

    @ApiModelProperty(value = "CPF.", required = true)
    private String patientTaxId;

    @ApiModelProperty(value = "Modo de check-in.", required = true)
    private CheckInMode mode;

    @ApiModelProperty(value = "Tempo estimado para dar entrada no hospital.", required = true)
    private EstimatedTimeArrival estimatedTimeArrival;

}