package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("PostUserRequestV1")
@Getter
@Builder
public class PostUserResponse {

    @ApiModelProperty(value = "ID do usuário.", required = true)
    private String userId;

}