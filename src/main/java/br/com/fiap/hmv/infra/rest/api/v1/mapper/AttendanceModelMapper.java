package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetAttendanceNextPatientResponse;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.AgeMapper.getTextAge;

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

}