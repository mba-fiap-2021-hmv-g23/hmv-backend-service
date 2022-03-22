package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInResponse;

public class CheckInModelMapper {

    public static CheckIn toCheckIn(PostCheckInRequest request) {
        return CheckIn.builder()
                .patient(Patient.builder()
                        .taxId(request.getPatientTaxId())
                        .build())
                .mode(request.getMode())
                .estimatedTimeArrival(request.getEstimatedTimeArrival())
                .build();
    }

    public static PostCheckInResponse toPostCheckInResponse(CheckIn checkIn) {
        return PostCheckInResponse.builder()
                .checkInId(checkIn.getCheckInId())
                .queuePatientsNumber(checkIn.getQueuePatientsNumber())
                .expectedOpeningHours(checkIn.getExpectedOpeningHours())
                .build();
    }

}