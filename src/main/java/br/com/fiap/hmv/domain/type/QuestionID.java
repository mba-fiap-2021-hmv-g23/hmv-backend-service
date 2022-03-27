package br.com.fiap.hmv.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static br.com.fiap.hmv.domain.type.AnswerType.NUMERICO;
import static br.com.fiap.hmv.domain.type.AnswerType.SELECAO;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum QuestionID {

    PESO("Peso", NUMERICO, null, null),
    ALTURA("Altura", NUMERICO, null, null),
    FUMANTE("Fumante", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{8, 2}),
    FREQUENCIA_EXERCICIOS("Frequência de Exercícios", SELECAO,
            new String[]{"Nenhuma", "Menos de 1 vez por semana", "1 vez por semana", "2 vezes ou mais por semana"},
            new Integer[]{8, 4, 4, 2}),
    DIABETICO("Diabético", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{16, 2}),
    HIPERTENSAO("Hipertensão",
            SELECAO, new String[]{"Sim", "Não"},
            new Integer[]{16, 2}),
    HISTORICO_DOENCA_CARDIACA("Histórico de doenças cardíaca", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{32, 2}),
    HISTORICO_DOENCA_CARDIACA_FAMILIAR("Histórico de doenças cardíacas familiar", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{8, 2}),
    DORES_NO_PEITO("Dores no peito", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{32, 2}),
    TONTURA_E_VERTIGENS("Tonturas ou vertigens", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{16, 2}),
    ENJOO_PERDA_APETITE("Enjoo ou perda de apetite", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{16, 2}),
    DORES_NOS_BRACOS("Dores nos braços", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{16, 2}),
    FRAQUEZA("Fraqueza ou cansaço excessivo", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{16, 2}),
    DORES_REGIAO_ESTOMAGO("Dores na região do estomago", SELECAO,
            new String[]{"Sim", "Não"},
            new Integer[]{16, 2});

    private String questionTitle;
    private AnswerType answerType;
    private String[] options;
    private Integer[] answerWeight;

}