package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel("PostRefreshTokenRequestV1")
@Getter
public class PostRefreshTokenRequest {

    @ApiModelProperty(value = "Token de renovação de acesso.", required = true)
    private String refreshToken;

}