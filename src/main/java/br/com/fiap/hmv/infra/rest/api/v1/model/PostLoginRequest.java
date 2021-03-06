package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value = "PostLoginRequestV1")
@Getter
public class PostLoginRequest {

    @ApiModelProperty(value = "Nome de usuário ou CPF.", required = true)
    private String login;

    @ApiModelProperty(value = "Senha.", required = true)
    private String password;

}