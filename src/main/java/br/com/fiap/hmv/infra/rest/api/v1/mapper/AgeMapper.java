package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

public class AgeMapper {

    public static String getTextAge(LocalDate birthDate) {
        String ageText = null;
        if (Objects.nonNull(birthDate)) {
            long diffY = YEARS.between(birthDate, now());
            if (diffY < 0L) {
                long diffM = MONTHS.between(birthDate, now());
                if (diffM < 0) {
                    ageText = diffY + "Menor de 1 mês";
                } else if (diffM == 1L) {
                    ageText = "1 mês";
                } else if (diffM > 1) {
                    ageText = diffM + " meses";
                }
            } else if (diffY == 1L) {
                ageText = "1 ano";
            } else if (diffY > 1) {
                ageText = diffY + " anos";
            }
        }
        return ageText;
    }

}