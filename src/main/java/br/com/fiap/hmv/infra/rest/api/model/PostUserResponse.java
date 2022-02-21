package br.com.fiap.hmv.infra.rest.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("PostUserRequestV2")
@Getter
@Builder
public class PostUserResponse {

    @ApiModelProperty(value = "ID do usuário.", required = true)
    private String userId;

}