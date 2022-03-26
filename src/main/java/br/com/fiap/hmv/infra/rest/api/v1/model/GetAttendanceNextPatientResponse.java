package br.com.fiap.hmv.infra.rest.api.v1.model;

import br.com.fiap.hmv.domain.type.Genre;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("PostCheckInResponseV1")
@Getter
@Builder
public class GetAttendanceNextPatientResponse {

    @ApiModelProperty(value = "ID do Check-In.", required = true)
    private String checkInId;

    @ApiModelProperty(value = "ID do paciente.", required = true)
    private String patientId;

    @ApiModelProperty(value = "CPF do paciente.", required = true)
    private String patientTaxId;

    @ApiModelProperty(value = "Nome completo do paciente.", required = true)
    private String fullName;

    @ApiModelProperty(value = "Sexo do paciente.", required = true)
    private Genre genre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Data de nascimento do paciente.", required = true)
    private LocalDate birthDate;

    @ApiModelProperty(value = "Idade do paciente.", required = true)
    private String age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de check-in.", required = true)
    private final LocalDateTime checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de expiração do Check-In.", required = true)
    private LocalDateTime expiresDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data da última chamada do paciente à atendimento.")
    private LocalDateTime lastCallDate;

}