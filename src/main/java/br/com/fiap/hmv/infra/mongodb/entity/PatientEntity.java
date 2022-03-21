package br.com.fiap.hmv.infra.mongodb.entity;

import br.com.fiap.hmv.domain.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Document(collection = "patient")
public class PatientEntity {

    @Id
    private String patientId;

    @Indexed(name = "patient.taxId.unique-index", unique = true)
    private String taxId;

    private String email;
    private String fullName;
    private String phone;
    private String cellphone;
    private LocalDate birthDate;
    private Genre genre;
    private AddressEntity address;
    private String healthInsurance;
    private String healthCardNumber;

}