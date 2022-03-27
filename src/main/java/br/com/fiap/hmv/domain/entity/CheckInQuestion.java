package br.com.fiap.hmv.domain.entity;

import br.com.fiap.hmv.domain.type.QuestionID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckInQuestion {

    private QuestionID questionID;
    private String answer;

}