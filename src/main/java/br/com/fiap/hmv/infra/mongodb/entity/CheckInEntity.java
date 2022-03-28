package br.com.fiap.hmv.infra.mongodb.entity;

import br.com.fiap.hmv.domain.type.EstimatedTimeArrival;
import br.com.fiap.hmv.domain.type.Genre;
import br.com.fiap.hmv.domain.type.RiskClassification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "checkIn")
public class CheckInEntity {

    @Transient
    public static final long TTL_MINUTES = 60 * 24 * 7;

    @Id
    private String checkInId;
    private String patientId;
    private String patientTaxId;
    private String patientFullName;
    private String patientPhone;
    private LocalDate patientBirthDate;
    private Genre patientGenre;
    private String attendantId;
    private String attendantFullName;
    private String serviceDesk;
    private Integer calls;
    private Integer noShows;
    private EstimatedTimeArrival estimatedTimeArrival;
    private RiskClassification riskClassification;
    private LocalDateTime lastCallDate;
    private LocalDateTime serviceStartDate;
    private LocalDateTime serviceStartBaseDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime expiresDate;
    private LocalDateTime reservedAttendantDate;
    private LocalDateTime cancellationDate;
    private String cancellationReason;
    private List<CheckInQuestionEntity> formAnswers;

    @Indexed(name = "checkIn.ttl.index", expireAfterSeconds = 0)
    private LocalDateTime ttl;

}