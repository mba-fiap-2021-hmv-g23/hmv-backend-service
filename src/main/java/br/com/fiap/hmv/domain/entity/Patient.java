package br.com.fiap.hmv.domain.entity;

import br.com.fiap.hmv.domain.Genre;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Patient {

    private String patientId;
    private String taxId;
    private String email;
    private String fullName;
    private String phone;
    private String cellphone;
    private LocalDate birthDate;
    private Genre genre;
    private String healthInsurance;
    private String healthCardNumber;

}