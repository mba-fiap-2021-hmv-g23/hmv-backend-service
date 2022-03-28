package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetPatientResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostPatientRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostPatientResponse;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.AddressModelMapper.toAddress;
import static br.com.fiap.hmv.infra.rest.api.v1.mapper.AddressModelMapper.toAddressModel;

public class PatientModelMapper {

    public static Patient toPatient(PostPatientRequest request) {
        return Patient.builder()
                .patientTaxId(request.getTaxId())
                .fullName(request.getFullName())
                .genre(request.getGenre())
                .email(request.getEmail())
                .phone(request.getPhone())
                .cellphone(request.getCellphone())
                .birthDate(request.getBirthDate())
                .address(toAddress(request.getAddress()))
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
                .taxId(patient.getPatientTaxId())
                .fullName(patient.getFullName())
                .genre(patient.getGenre())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .cellphone(patient.getCellphone())
                .birthDate(patient.getBirthDate())
                .address(toAddressModel(patient.getAddress()))
                .healthInsurance(patient.getHealthInsurance())
                .healthCardNumber(patient.getHealthCardNumber())
                .build();
    }

}