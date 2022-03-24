package br.com.fiap.hmv.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum RiskClassification {

    EMERGENCIA(0),
    MUITO_URGENTE(10),
    URGENTE(50),
    POUCO_URGENTE(120),
    NAO_URGENTE(240);

    private Integer minutes;

}