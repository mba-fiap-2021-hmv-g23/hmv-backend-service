package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("PostCheckInResponseV2")
@Getter
@Builder
public class PostCheckInResponse {

    @ApiModelProperty(value = "ID do Check-In.", required = true)
    private String checkInId;

}