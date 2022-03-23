package br.com.fiap.hmv.infra.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("PostLoginResponseV1")
@Getter
@Builder
public class PostLoginResponse {

    @ApiModelProperty(value = "Token de acesso.", required = true)
    private final String accessToken;

    @ApiModelProperty(value = "Token de renovação de acesso.", required = true)
    private final String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de validade do token de acesso.", required = true)
    private final LocalDateTime expiresIn;

    @ApiModelProperty(value = "ID do usuário.", required = true)
    private String userId;

    @ApiModelProperty(value = "Nome de usuário.", required = true)
    private final String username;

    @ApiModelProperty(value = "CPF.", required = true)
    private final String userTaxId;

    @ApiModelProperty(value = "Nome completo.", required = true)
    private final String fullName;

    @ApiModelProperty(value = "ID do paciente.", required = true)
    private String patientId;
    
}