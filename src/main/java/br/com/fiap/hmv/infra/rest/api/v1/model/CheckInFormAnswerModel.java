package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("CheckInQuestionModelV1")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInFormAnswerModel {

    @ApiModelProperty(value = "ID da pergunta.", required = true)
    private String questionId;

    @ApiModelProperty(value = "Resposta.", required = true)
    private String answer;

}