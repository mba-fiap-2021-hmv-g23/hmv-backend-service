package br.com.fiap.hmv.domain.entity;

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
    private LocalDateTime expiresDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime serviceStartDate;

}