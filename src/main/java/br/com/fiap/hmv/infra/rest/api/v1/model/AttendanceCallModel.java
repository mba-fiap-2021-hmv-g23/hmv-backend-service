package br.com.fiap.hmv.infra.rest.api.v1.model;

import br.com.fiap.hmv.domain.type.Genre;
import br.com.fiap.hmv.domain.type.RiskClassification;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(value = "AttendanceCallModelV1")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCallModel {

    @ApiModelProperty(value = "ID do Check-In.", required = true)
    private String checkInId;

    @ApiModelProperty(value = "Nome completo do paciente.", required = true)
    private String fullName;

    @ApiModelProperty(value = "Sexo do paciente.", required = true)
    private Genre genre;

    @ApiModelProperty(value = "Idade do paciente.", required = true)
    private String age;

    @ApiModelProperty(value = "Classificação do risco do paciente.", required = true)
    private RiskClassification riskClassification;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data da última chamada do paciente à atendimento.")
    private LocalDateTime lastCallDate;

}