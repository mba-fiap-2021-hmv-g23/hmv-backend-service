package br.com.fiap.hmv.infra.rest.api.mapper;

import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.model.GetPatientResponse;
import br.com.fiap.hmv.infra.rest.api.model.PostPatientRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostPatientResponse;

public class PatientApiModelMapper {

    public static Patient toPatient(PostPatientRequest request) {
        return Patient.builder()
                .taxId(request.getTaxId())
                .fullName(request.getFullName())
                .genre(request.getGenre())
                .email(request.getEmail())
                .phone(request.getPhone())
                .cellphone(request.getCellphone())
                .birthDate(request.getBirthDate())
                .healthInsurance(request.getHealthInsurance())
                .healthCardNumber(request.getHealthCardNumber())
                .build();
    }

    public static PostPatientResponse toPostPatientResponse(Patient patient) {
        return PostPatientResponse.builder()
                .patientId(patient.getPatientId())
                .build();
    }

    public static GetPatientResponse toGetPatientResponse(Patient patient) {
        return GetPatientResponse.builder()
                .patientId(patient.getPatientId())
                .taxId(patient.getTaxId())
                .fullName(patient.getFullName())
                .genre(patient.getGenre())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .cellphone(patient.getCellphone())
                .birthDate(patient.getBirthDate())
                .healthInsurance(patient.getHealthInsurance())
                .healthCardNumber(patient.getHealthCardNumber())
                .build();
    }

}