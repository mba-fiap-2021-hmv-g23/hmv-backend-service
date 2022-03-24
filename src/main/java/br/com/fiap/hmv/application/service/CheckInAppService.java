package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.type.RiskClassification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static br.com.fiap.hmv.domain.service.RiskClassificationService.calculateRiskClassification;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckInAppService {

    private final CheckInPort checkInPort;

    public Mono<Void> checkIn(final CheckIn checkIn) {
        log.info("[APPLICATION_SERVICE] Iniciando check-in do paciente.");
        RiskClassification riskClassification = calculateRiskClassification();
        LocalDateTime serviceStartBaseDate = now();
        serviceStartBaseDate
                .plus(riskClassification.getMinutes(), MINUTES)
                .plus(checkIn.getEstimatedTimeArrival().getMinutes(), MINUTES);
        checkIn.setRiskClassification(riskClassification);
        checkIn.setServiceStartBaseDate(serviceStartBaseDate);
        return checkInPort.insert(checkIn);
    }

}