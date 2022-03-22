package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;

public class CheckInEntityMapper {

    public static CheckInEntity toCheckInEntity(CheckIn checkIn) {
        return CheckInEntity.builder()
                .checkInId(checkIn.getCheckInId())
                .patientTaxId(checkIn.getPatient().getTaxId())
                .inclusionDate(checkIn.getInclusionDate())
                .estimatedTimeArrival(checkIn.getEstimatedTimeArrival())
                .queuePatientsNumber(checkIn.getQueuePatientsNumber())
                .ttl(now().plus(2, MINUTES))
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