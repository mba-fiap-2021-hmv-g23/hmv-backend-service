package br.com.fiap.hmv.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;


@Getter
@AllArgsConstructor(access = PRIVATE)
public enum EstimatedTimeArrival {

    ESTA_NO_LOCAL(1),
    NO_LOCAL_EM_ATE_30_MINUTOS(30),
    NO_LOCAL_EM_ATE_1_HORA(60),
    NO_LOCAL_APOS_1_HORA(90);

    private int minutes;

}