package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.AttendanceQueueCalls;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.rest.api.v1.model.AttendanceCallModel;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceNextPatientResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceQueueCallsResponse;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.AgeMapper.getTextAge;
import static java.util.stream.Collectors.toList;

public class AttendanceModelMapper {

    public static GetAttendanceNextPatientResponse toGetAttendanceNextPatientResponse(CheckIn checkIn) {
        return GetAttendanceNextPatientResponse.builder()
                .checkInId(checkIn.getCheckInId())
                .patientId(checkIn.getPatient().getPatientId())
                .patientTaxId(checkIn.getPatient().getTaxId())
                .fullName(checkIn.getPatient().getFullName())
                .birthDate(checkIn.getPatient().getBirthDate())
                .age(getTextAge(checkIn.getPatient().getBirthDate()))
                .genre(checkIn.getPatient().getGenre())
                .checkInDate(checkIn.getInclusionDate())
                .expiresDate(checkIn.getExpiresDate())
                .build();
    }

    public static GetAttendanceQueueCallsResponse toGetAttendanceQueueCallsResponse(
            AttendanceQueueCalls attendanceQueueCalls
    ) {
        return GetAttendanceQueueCallsResponse.builder()
                .lastCalls(attendanceQueueCalls.getAwaitingCall().stream()
                        .map(AttendanceModelMapper::toAttendanceCallModel)
                        .collect(toList()))
                .inCall(attendanceQueueCalls.getInCall().stream()
                        .map(AttendanceModelMapper::toAttendanceCallModel)
                        .collect(toList()))
                .awaitingCall(attendanceQueueCalls.getLastCalls().stream()
                        .map(AttendanceModelMapper::toAttendanceCallModel)
                        .collect(toList()))
                .build();
    }

    private static AttendanceCallModel toAttendanceCallModel(CheckIn checkIn) {
        return AttendanceCallModel.builder()
                .checkInId(checkIn.getCheckInId())
                .fullName(checkIn.getPatient().getFullName())
                .age(getTextAge(checkIn.getPatient().getBirthDate()))
                .genre(checkIn.getPatient().getGenre())
                .classification("EMERGÊNCIA")
                .build();
    }

}