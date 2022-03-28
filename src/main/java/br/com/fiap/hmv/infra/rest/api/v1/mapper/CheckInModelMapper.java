package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.CheckInQuestion;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.domain.type.QuestionID;
import br.com.fiap.hmv.infra.rest.api.v1.model.CheckInFormAnswerModel;
import br.com.fiap.hmv.infra.rest.api.v1.model.DeleteCheckInCancelRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetCheckInFormResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetCheckInResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PutCheckInResponse;

import java.util.stream.Collectors;

import static br.com.fiap.hmv.infra.rest.api.v1.mapper.AgeMapper.getTextAge;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class CheckInModelMapper {

    public static CheckIn toCheckIn(String patientId, PostCheckInRequest request) {
        return CheckIn.builder()
                .patient(Patient.builder()
                        .patientId(patientId)
                        .build())
                .estimatedTimeArrival(request.getEstimatedTimeArrival())
                .formAnswers(request.getFormAnswers().stream().map(formAnswerModel -> CheckInQuestion.builder()
                        .questionID(formAnswerModel.getQuestionId())
                        .answer(formAnswerModel.getAnswer()).build()).collect(Collectors.toList()))
                .build();
    }

    public static CheckIn toCheckIn(String checkInId, DeleteCheckInCancelRequest request) {
        return CheckIn.builder()
                .checkInId(checkInId)
                .cancellationReason(request.getReason())
                .build();
    }

    public static PostCheckInResponse toPostCheckInResponse(CheckIn checkIn) {
        return PostCheckInResponse.builder()
                .checkInId(checkIn.getCheckInId())
                .checkInDate(checkIn.getInclusionDate())
                .expiresDate(checkIn.getExpiresDate())
                .build();
    }

    public static PutCheckInResponse toPutCheckInResponse(CheckIn checkIn) {
        return PutCheckInResponse.builder()
                .checkInId(checkIn.getCheckInId())
                .checkInDate(checkIn.getInclusionDate())
                .expiresDate(checkIn.getExpiresDate())
                .build();
    }

    public static GetCheckInResponse toGetCheckInResponse(CheckIn checkIn) {
        return GetCheckInResponse.builder()
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
                .estimatedTimeArrival(checkIn.getEstimatedTimeArrival())
                .formAnswers(checkIn.getFormAnswers().stream().map(question -> CheckInFormAnswerModel.builder()
                        .questionId(question.getQuestionID())
                        .answer(question.getAnswer())
                        .build()).collect(toList()))
                .build();
    }

    public static GetCheckInFormResponse toGetCheckInFormResponse(QuestionID questionID) {
        return GetCheckInFormResponse.builder()
                .questionId(questionID.name())
                .questionTitle(questionID.getQuestionTitle())
                .answerType(questionID.getAnswerType())
                .options(questionID.getOptions())
                .build();
    }
}