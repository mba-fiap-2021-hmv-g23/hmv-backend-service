package br.com.fiap.hmv.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;


@Getter
@AllArgsConstructor(access = PRIVATE)
public enum EstimatedTimeArrival {

    ESTA_NO_LOCAL(1),
    NO_LOCAL_EM_ATE_15_MINUTOS(14),
    NO_LOCAL_EM_ATE_30_MINUTOS(29),
    NO_LOCAL_APOS_30_MINUTOS(31);

    private int minutes;

}