package br.com.fiap.hmv.infra.rest.api.mapper;

import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.model.PostPatientRequest;
import br.com.fiap.hmv.infra.rest.api.model.PostPatientResponse;

public class PatientApiModelMapper {

    public static Patient toPatient(PostPatientRequest request) {
        return Patient.builder()
                .build();
    }

    public static PostPatientResponse toPostPatientResponse(Patient patient) {
        return PostPatientResponse.builder()
                .build();
    }

}