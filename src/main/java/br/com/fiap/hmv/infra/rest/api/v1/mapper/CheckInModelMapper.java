package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.CheckInQuestion;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.domain.type.QuestionID;
import br.com.fiap.hmv.infra.rest.api.v1.model.DeleteCheckInCancelRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.GetCheckInFormResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostCheckInResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PutCheckInResponse;

import java.util.stream.Collectors;

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

    public static GetCheckInFormResponse toGetCheckInFormResponse(QuestionID questionID) {
        return GetCheckInFormResponse.builder()
                .questionId(questionID.name())
                .questionTitle(questionID.getQuestionTitle())
                .answerType(questionID.getAnswerType())
                .options(questionID.getOptions())
                .build();
    }

}