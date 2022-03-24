package br.com.fiap.hmv.infra.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel("PostCheckInResponseV1")
@Getter
@Builder
public class PostCheckInResponse {

    @ApiModelProperty(value = "ID do Check-In.", required = true)
    private String checkInId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de Check-In.", required = true)
    private final LocalDateTime checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de expiração do Check-In.", required = true)
    private LocalDateTime expiresDate;

}