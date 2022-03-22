package br.com.fiap.hmv.infra.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(value = "ApiModelError")
@Getter
@Builder
public class ApiModelError {


    @ApiModelProperty(value = "Data e hora do retorno requisição.", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "Código do status HTTP.", required = true)
    private Integer status;

    @ApiModelProperty(value = "Tipo do erro", required = true)
    private String error;

    @ApiModelProperty(value = "Mensagem de erro.", required = true)
    private String message;
}
