package br.com.fiap.hmv.infra.mongodb.entity;

import br.com.fiap.hmv.domain.type.EstimatedTimeArrival;
import br.com.fiap.hmv.domain.type.RiskClassification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "checkIn")
public class CheckInEntity {

    @Transient
    public static final long TTL_MINUTES = 60 * 24 * 2;

    @Id
    private String checkInId;

    private String patientId;
    private EstimatedTimeArrival estimatedTimeArrival;
    private RiskClassification riskClassification;
    private LocalDateTime serviceStartDate;
    private LocalDateTime serviceStartBaseDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime expiresDate;

    @Indexed(name = "checkIn.ttl.index", expireAfterSeconds = 0)
    private LocalDateTime ttl;

}