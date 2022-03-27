package br.com.fiap.hmv.infra.rest.api.v1.model;

import br.com.fiap.hmv.domain.type.AnswerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("GetCheckInFormResponseV1")
@Getter
@Builder
public class GetCheckInFormResponse {

    @ApiModelProperty(value = "ID da pergunta.", required = true)
    private String questionId;

    @ApiModelProperty(value = "Título da resposta.", required = true)
    private String questionTitle;

    @ApiModelProperty(value = "Tipo da resposta.", required = true)
    private AnswerType answerType;

    @ApiModelProperty(value = "Opções de seleção da resposta.", required = true)
    private String[] options;

}