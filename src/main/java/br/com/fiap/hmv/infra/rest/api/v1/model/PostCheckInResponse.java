package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("PostCheckInResponseV2")
@Getter
@Builder
public class PostCheckInResponse {

    @ApiModelProperty(value = "ID do Check-In.", required = true)
    private String checkInId;

    @ApiModelProperty(value = "Tempo estimado para atendimento.", required = true)
    private LocalDateTime expectedOpeningHours;

    @ApiModelProperty(value = "Quantidade de pacientes à frente na fila de atendimento.", required = true)
    private String queuePatientsNumber;

}