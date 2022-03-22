package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;

public class CheckInEntityMapper {

    public static CheckInEntity toCheckInEntity(CheckIn checkIn) {
        return CheckInEntity.builder()
                .checkInId(checkIn.getCheckInId())
                .patientTaxId(checkIn.getPatient().getTaxId())
                .inclusionDate(checkIn.getInclusionDate())
                .estimatedTimeArrival(checkIn.getEstimatedTimeArrival())
                .queuePatientsNumber(checkIn.getQueuePatientsNumber())
                .build();
    }

    public static CheckIn toCheckIn(CheckInEntity checkInEntity) {
        return CheckIn.builder()
                .checkInId(checkInEntity.getCheckInId())
                .patient(Patient.builder().taxId(checkInEntity.getPatientTaxId()).build())
                .inclusionDate(checkInEntity.getInclusionDate())
                .estimatedTimeArrival(checkInEntity.getEstimatedTimeArrival())
                .queuePatientsNumber(checkInEntity.getQueuePatientsNumber())
                .build();
    }

}