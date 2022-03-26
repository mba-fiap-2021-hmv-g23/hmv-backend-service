package br.com.fiap.hmv.domain.service;

import br.com.fiap.hmv.domain.type.RiskClassification;

import java.util.Random;

public class RiskClassificationService {

    public static RiskClassification calculateRiskClassification() {
        int index = new Random().nextInt(RiskClassification.values().length);
//        return RiskClassification.values()[index];
        return RiskClassification.EMERGENCIA;
    }

}