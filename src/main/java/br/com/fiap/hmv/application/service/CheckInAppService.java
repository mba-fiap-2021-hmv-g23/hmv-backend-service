package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.exception.HttpException;
import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.application.port.PatientPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.type.EstimatedTimeArrival;
import br.com.fiap.hmv.domain.type.QuestionID;
import br.com.fiap.hmv.domain.type.RiskClassification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;

import static br.com.fiap.hmv.domain.service.RiskClassificationService.calculateRiskClassification;
import static br.com.fiap.hmv.domain.type.EstimatedTimeArrival.ESTA_NO_LOCAL;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckInAppService {

    private final CheckInPort checkInPort;
    private final PatientPort patientPort;

    public Mono<Void> checkIn(final CheckIn checkIn) {
        log.info("[APPLICATION_SERVICE] Iniciando check-in do paciente.");
        return patientPort.findById(checkIn.getPatient().getPatientId()).flatMap(patient -> {
            LocalDateTime now = now();
            RiskClassification riskClassification = calculateRiskClassification();
            checkIn.setPatient(patient);
            checkIn.setRiskClassification(riskClassification);
            checkIn.setServiceStartBaseDate(now
                    .plus(riskClassification.getMinutes(), MINUTES)
                    .plus(checkIn.getEstimatedTimeArrival().getMinutes(), MINUTES));
            checkIn.setInclusionDate(now);
            checkIn.setCalls(0);
            checkIn.setNoShows(0);
            return checkInPort.insert(checkIn);
        });
    }

    public Mono<CheckIn> confirm(String checkInId) {
        log.info("[APPLICATION_SERVICE] Iniciando confirmação do check-in do paciente.");
        return checkInPort.getById(checkInId).flatMap(checkIn -> {
            if (checkIn.getEstimatedTimeArrival() != ESTA_NO_LOCAL) {
                LocalDateTime now = now();
                RiskClassification riskClassification = checkIn.getRiskClassification();
                EstimatedTimeArrival estimatedTimeArrival = ESTA_NO_LOCAL;
                checkIn.setServiceStartBaseDate(now
                        .plus(riskClassification.getMinutes(), MINUTES)
                        .plus(estimatedTimeArrival.getMinutes(), MINUTES));
                checkIn.setEstimatedTimeArrival(estimatedTimeArrival);
                checkIn.setInclusionDate(now);
                return checkInPort.update(checkIn).thenReturn(checkIn);
            } else {
                return Mono.error(new HttpException("Sua presença já foi confirmada. " +
                        "Aguarde que em breve você será chamado para atendimento.", BAD_REQUEST));
            }
        });
    }

    public Mono<Void> cancel(CheckIn checkIn) {
        return Mono.empty();
    }

    public Flux<QuestionID> getForm(String patientId) {
        return Flux.fromStream(Arrays.stream(QuestionID.values()).sequential());
    }

}