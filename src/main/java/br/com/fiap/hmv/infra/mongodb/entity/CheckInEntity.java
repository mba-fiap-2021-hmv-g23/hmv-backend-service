package br.com.fiap.hmv.infra.mongodb.entity;

import br.com.fiap.hmv.domain.entity.EstimatedTimeArrival;
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

    private String patientTaxId;
    private EstimatedTimeArrival estimatedTimeArrival;
    private LocalDateTime expiresDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime serviceStartDate;

    @Indexed(name = "checkIn.ttl.index", expireAfterSeconds = 0)
    private LocalDateTime ttl;

}