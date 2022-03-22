package br.com.fiap.hmv.infra.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("PostRefreshTokenResponseV1")
@Getter
@Builder
public class PostRefreshTokenResponse {

    @ApiModelProperty(value = "Token de acesso.", required = true)
    private final String accessToken;

    @ApiModelProperty(value = "Token de renovação de acesso.", required = true)
    private final String refreshToken;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de validade do token de acesso.", required = true)
    private final LocalDateTime expiresIn;

}