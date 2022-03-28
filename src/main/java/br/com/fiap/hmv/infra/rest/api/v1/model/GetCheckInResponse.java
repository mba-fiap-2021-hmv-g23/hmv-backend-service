package br.com.fiap.hmv.infra.rest.api.v1.model;

import br.com.fiap.hmv.domain.type.EstimatedTimeArrival;
import br.com.fiap.hmv.domain.type.Genre;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("GetCheckInResponseV1")
@Getter
@Builder
public class GetCheckInResponse {

    @ApiModelProperty(value = "ID do Check-In.", required = true)
    private String checkInId;

    @ApiModelProperty(value = "ID do paciente.", required = true)
    private String patientId;

    @ApiModelProperty(value = "CPF do paciente.", required = true)
    private String patientTaxId;

    @ApiModelProperty(value = "Nome completo do paciente.", required = true)
    private String patientFullName;

    @ApiModelProperty(value = "Sexo do paciente.", required = true)
    private Genre patientGenre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Data de nascimento do paciente.", required = true)
    private LocalDate patientBirthDate;

    @ApiModelProperty(value = "Idade do paciente.", required = true)
    private String patientAge;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de check-in.", required = true)
    private final LocalDateTime checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de expiração do Check-In.", required = true)
    private LocalDateTime expiresDate;

    @ApiModelProperty(value = "Nome completo do atendente.")
    private String attendantFullName;

    @ApiModelProperty(value = "Balcão de atendimento.")
    private String serviceDesk;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data da última chamada do paciente à atendimento.")
    private LocalDateTime lastCallDate;

    @ApiModelProperty(value = "Tempo estimado para dar entrada no hospital.", required = true)
    private EstimatedTimeArrival estimatedTimeArrival;

    @ApiModelProperty(value = "Respostas do formulário de check-in.", required = true)
    private List<CheckInFormAnswerModel> formAnswers;

}