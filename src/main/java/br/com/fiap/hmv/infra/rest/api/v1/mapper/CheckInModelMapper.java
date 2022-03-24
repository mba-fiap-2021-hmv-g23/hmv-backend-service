package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInResponse;

public class CheckInModelMapper {

    public static CheckIn toCheckIn(String patientId, PostCheckInRequest request) {
        return CheckIn.builder()
                .patient(Patient.builder()
                        .patientId(patientId)
                        .build())
                .estimatedTimeArrival(request.getEstimatedTimeArrival())
                .build();
    }

    public static PostCheckInResponse toPostCheckInResponse(CheckIn checkIn) {
        return PostCheckInResponse.builder()
                .checkInId(checkIn.getCheckInId())
                .checkInDate(checkIn.getInclusionDate())
                .expiresDate(checkIn.getExpiresDate())
                .build();
    }

}