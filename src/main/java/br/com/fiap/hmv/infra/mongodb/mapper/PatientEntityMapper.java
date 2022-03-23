package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.mongodb.entity.PatientEntity;

import static br.com.fiap.hmv.infra.mongodb.mapper.AddressEntityMapper.toAddress;
import static br.com.fiap.hmv.infra.mongodb.mapper.AddressEntityMapper.toAddressEntity;

public class PatientEntityMapper {

    public static PatientEntity toPatientEntity(Patient patient) {
        return PatientEntity.builder()
                .patientId(patient.getPatientId())
                .taxId(patient.getTaxId())
                .fullName(patient.getFullName())
                .birthDate(patient.getBirthDate())
                .genre(patient.getGenre())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .cellphone(patient.getCellphone())
                .address(toAddressEntity(patient.getAddress()))
                .healthInsurance(patient.getHealthInsurance())
                .healthCardNumber(patient.getHealthCardNumber())
                .build();
    }

    public static Patient toPatient(PatientEntity entity) {
        return Patient.builder()
                .patientId(entity.getPatientId())
                .taxId(entity.getTaxId())
                .fullName(entity.getFullName())
                .birthDate(entity.getBirthDate())
                .genre(entity.getGenre())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .cellphone(entity.getCellphone())
                .address(toAddress(entity.getAddress()))
                .healthInsurance(entity.getHealthInsurance())
                .healthCardNumber(entity.getHealthCardNumber())
                .build();
    }

}