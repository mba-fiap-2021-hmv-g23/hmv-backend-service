package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.AttendanceQueueCalls;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.rest.api.v1.model.AttendanceCallModel;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceNextPatientResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceQueueCallsResponse;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.AgeMapper.getTextAge;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class AttendanceModelMapper {

    public static GetAttendanceNextPatientResponse toGetAttendanceNextPatientResponse(CheckIn checkIn) {
        return GetAttendanceNextPatientResponse.builder()
                .checkInId(checkIn.getCheckInId())
                .patientId(checkIn.getPatient().getPatientId())
                .patientTaxId(checkIn.getPatient().getPatientTaxId())
                .patientFullName(checkIn.getPatient().getFullName())
                .patientBirthDate(checkIn.getPatient().getBirthDate())
                .patientAge(getTextAge(checkIn.getPatient().getBirthDate()))
                .patientGenre(checkIn.getPatient().getGenre())
                .checkInDate(checkIn.getInclusionDate())
                .expiresDate(checkIn.getExpiresDate())
                .attendantFullName(nonNull(checkIn.getAttendant()) ? checkIn.getAttendant().getFullName() : null)
                .serviceDesk(checkIn.getServiceDesk())
                .lastCallDate(checkIn.getLastCallDate())
                .build();
    }

    public static GetAttendanceQueueCallsResponse toGetAttendanceQueueCallsResponse(
            AttendanceQueueCalls attendanceQueueCalls
    ) {
        return GetAttendanceQueueCallsResponse.builder()
                .inCall(attendanceQueueCalls.getInCall().stream()
                        .map(AttendanceModelMapper::toAttendanceCallModel)
                        .collect(toList()))
                .lastCalls(attendanceQueueCalls.getLastCalls().stream()
                        .map(AttendanceModelMapper::toAttendanceCallModel)
                        .collect(toList()))
                .awaitingCall(attendanceQueueCalls.getAwaitingCall().stream()
                        .map(AttendanceModelMapper::toAttendanceCallModel)
                        .collect(toList()))
                .build();
    }

    private static AttendanceCallModel toAttendanceCallModel(CheckIn checkIn) {
        return AttendanceCallModel.builder()
                .checkInId(checkIn.getCheckInId())
                .patientFullName(checkIn.getPatient().getFullName())
                .patientAge(getTextAge(checkIn.getPatient().getBirthDate()))
                .patientGenre(checkIn.getPatient().getGenre())
                .riskClassification(checkIn.getRiskClassification())
                .lastCallDate(checkIn.getLastCallDate())
                .attendantFullName(nonNull(checkIn.getAttendant()) ? checkIn.getAttendant().getFullName() : null)
                .serviceDesk(checkIn.getServiceDesk())
                .build();
    }

}