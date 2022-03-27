package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.CheckInQuestion;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity;
import br.com.fiap.hmv.infra.mongodb.entity.CheckInQuestionEntity;

import static br.com.fiap.hmv.infra.mongodb.entity.CheckInEntity.TTL_MINUTES;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

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
                .formAnswers(checkIn.getFormAnswers().stream().map(question -> CheckInQuestionEntity.builder()
                        .questionID(question.getQuestionID())
                        .answer(question.getAnswer())
                        .build()).collect(toList()))
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
                .lastCallDate(checkInEntity.getLastCallDate())
                .reservedAttendantDate(checkInEntity.getReservedAttendantDate())
                .inclusionDate(checkInEntity.getInclusionDate())
                .expiresDate(checkInEntity.getExpiresDate())
                .estimatedTimeArrival(checkInEntity.getEstimatedTimeArrival())
                .riskClassification(checkInEntity.getRiskClassification())
                .serviceStartBaseDate(checkInEntity.getServiceStartBaseDate())
                .formAnswers(checkInEntity.getFormAnswers().stream().map(questionEntity -> CheckInQuestion.builder()
                        .questionID(questionEntity.getQuestionID())
                        .answer(questionEntity.getAnswer())
                        .build()).collect(toList()))
                .build();
    }

}