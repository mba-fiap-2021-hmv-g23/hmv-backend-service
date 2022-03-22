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
    private CheckInMode mode;
    private EstimatedTimeArrival estimatedTimeArrival;
    private String queuePatientsNumber;
    private LocalDateTime expectedOpeningHours;
    private LocalDateTime inclusionDate;

}