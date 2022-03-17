package br.com.fiap.hmv.infra.rest.api.model;

import br.com.fiap.hmv.domain.Genre;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;

@ApiModel(value = "PostPatientRequest")
@Getter
public class PostPatientRequest {

    @ApiModelProperty(value = "CPF.", required = true)
    private String taxId;

    @ApiModelProperty(value = "Nome completo.", required = true)
    private String fullName;

    @ApiModelProperty(value = "E-mail.", required = true)
    private String email;

    @ApiModelProperty(value = "Telefone.", required = true)
    private String phone;

    @ApiModelProperty(value = "Celular.", required = true)
    private String cellphone;

    @ApiModelProperty(value = "Data de nascimento.", required = true)
    private LocalDate birthDate;

    @ApiModelProperty(value = "Sexo.", required = true)
    private Genre genre;

    @ApiModelProperty(value = "Plano de saúde.", required = true)
    private String healthInsurance;

    @ApiModelProperty(value = "Número da carteira de saúde.", required = true)
    private String healthCardNumber;

}