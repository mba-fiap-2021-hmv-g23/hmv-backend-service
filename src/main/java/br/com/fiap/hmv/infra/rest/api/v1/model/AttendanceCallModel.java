package br.com.fiap.hmv.infra.rest.api.v1.model;

import br.com.fiap.hmv.domain.entity.Genre;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String classification;

}