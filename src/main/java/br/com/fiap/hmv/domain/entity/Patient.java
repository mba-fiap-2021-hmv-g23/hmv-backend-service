package br.com.fiap.hmv.domain.entity;

import br.com.fiap.hmv.domain.type.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Patient {

    private String patientId;
    private String patientTaxId;
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    private Genre genre;
    private String cellphone;
    private String email;
    private Address address;
    private String healthInsurance;
    private String healthCardNumber;

}