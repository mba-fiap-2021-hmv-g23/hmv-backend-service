package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@ApiModel("GetAttendanceQueueCallsResponse")
@Getter
@Builder
public class GetAttendanceQueueCallsResponse {

    @ApiModelProperty(
            value = "Pacientes com chamada ativa para atendimento.",
            required = true,
            allowEmptyValue = true
    )
    private List<AttendanceCallModel> inCall;

    @ApiModelProperty(
            value = "Pacientes não se apresentaram para atendimento (últimos 10 mais recentes).",
            required = true,
            allowEmptyValue = true
    )
    private List<AttendanceCallModel> lastCalls;

    @ApiModelProperty(
            value = "Pacientes aguardando chamada à atendimento.",
            required = true,
            allowEmptyValue = true
    )
    private List<AttendanceCallModel> pendingCall;

    @ApiModelProperty(
            value = "Fila das próximas chamadas à atendimento (novas chamadas e re-chamadas).",
            required = true,
            allowEmptyValue = true
    )
    private List<AttendanceCallModel> awaitingCall;

}