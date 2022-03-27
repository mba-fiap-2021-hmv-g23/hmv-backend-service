package br.com.fiap.hmv.domain.entity;

import br.com.fiap.hmv.domain.type.EstimatedTimeArrival;
import br.com.fiap.hmv.domain.type.RiskClassification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CheckIn {

    private String checkInId;
    private Patient patient;
    private User attendant;
    private Integer calls;
    private Integer noShows;
    private EstimatedTimeArrival estimatedTimeArrival;
    private RiskClassification riskClassification;
    private LocalDateTime lastCallDate;
    private LocalDateTime expiresDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime serviceStartDate;
    private LocalDateTime serviceStartBaseDate;
    private LocalDateTime reservedAttendantDate;
    private LocalDateTime cancellationDate;
    private String cancellationReason;
    private List<CheckInQuestion> formAnswers;

}