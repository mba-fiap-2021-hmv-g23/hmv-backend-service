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

    @ApiModelProperty(value = "ID do Check-In.", required = true, allowEmptyValue = true)
    private List<AttendanceCallModel> lastCalls;

    @ApiModelProperty(value = "ID do Check-In.", required = true, allowEmptyValue = true)
    private List<AttendanceCallModel> inCall;

    @ApiModelProperty(value = "ID do Check-In.", required = true, allowEmptyValue = true)
    private List<AttendanceCallModel> awaitingCall;

}