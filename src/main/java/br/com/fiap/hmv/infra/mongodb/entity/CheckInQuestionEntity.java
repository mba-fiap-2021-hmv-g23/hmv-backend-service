package br.com.fiap.hmv.infra.mongodb.entity;

import br.com.fiap.hmv.domain.type.QuestionID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckInQuestionEntity {

    private QuestionID questionID;
    private String answer;

}