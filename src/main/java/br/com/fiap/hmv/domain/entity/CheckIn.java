package br.com.fiap.hmv.domain.entity;

import br.com.fiap.hmv.domain.type.EstimatedTimeArrival;
import br.com.fiap.hmv.domain.type.RiskClassification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CheckIn {

    private String checkInId;
    private Patient patient;
    private User attendant;
    private EstimatedTimeArrival estimatedTimeArrival;
    private RiskClassification riskClassification;
    private LocalDateTime expiresDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime serviceStartDate;
    private LocalDateTime serviceStartBaseDate;
    private Integer calls;
    private String noShows;
    private LocalDateTime lastCallDate;
    private LocalDateTime blockCallUntilDate;
    private LocalDateTime lastCallToStartDate;

}