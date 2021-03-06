package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel("PostUserRequestV1")
@Getter
public class PostUserRequest {

    @ApiModelProperty(value = "CPF.", required = true)
    private String taxId;

    @ApiModelProperty(value = "Nome completo.", required = true)
    private String fullName;

    @ApiModelProperty(value = "Nome de usuário.")
    private String username;

    @ApiModelProperty(value = "Senha.", required = true)
    private String password;

}