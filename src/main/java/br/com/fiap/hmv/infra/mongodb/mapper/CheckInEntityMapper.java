package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;

import static br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity.TTL_MINUTES;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.nonNull;

public class CheckInEntityMapper {

    public static CheckInEntity toCheckInEntity(CheckIn checkIn) {
        return CheckInEntity.builder()
                .checkInId(checkIn.getCheckInId())
                .patientId(checkIn.getPatient().getPatientId())
                .calls(checkIn.getCalls())
                .noShows(checkIn.getNoShows())
                .inclusionDate(checkIn.getInclusionDate())
                .expiresDate(checkIn.getExpiresDate())
                .estimatedTimeArrival(checkIn.getEstimatedTimeArrival())
                .riskClassification(checkIn.getRiskClassification())
                .serviceStartBaseDate(checkIn.getServiceStartBaseDate())
                .ttl(now().plus(TTL_MINUTES, MINUTES))
                .build();
    }

    public static CheckIn toCheckIn(CheckInEntity checkInEntity) {
        return CheckIn.builder()
                .checkInId(checkInEntity.getCheckInId())
                .patient(Patient.builder().patientId(checkInEntity.getPatientId()).build())
                .attendant(nonNull(checkInEntity.getAttendantId()) ?
                        User.builder().userId(checkInEntity.getAttendantId()).build()
                        : null
                )
                .calls(checkInEntity.getCalls())
                .noShows(checkInEntity.getNoShows())
                .reservedAttendantDate(checkInEntity.getReservedAttendantDate())
                .inclusionDate(checkInEntity.getInclusionDate())
                .expiresDate(checkInEntity.getExpiresDate())
                .estimatedTimeArrival(checkInEntity.getEstimatedTimeArrival())
                .riskClassification(checkInEntity.getRiskClassification())
                .serviceStartBaseDate(checkInEntity.getServiceStartBaseDate())
                .build();
    }

}