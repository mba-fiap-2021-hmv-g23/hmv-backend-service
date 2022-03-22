package br.com.fiap.hmv.infra.mongodb.entity;

import br.com.fiap.hmv.domain.entity.EstimatedTimeArrival;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "checkIn")
public class CheckInEntity {

    @Id
    private String checkInId;

    private String patientTaxId;
    private String attendantId;
    private EstimatedTimeArrival estimatedTimeArrival;
    private Integer queuePatientsNumber;
    private LocalDateTime estimatedOpeningHours;
    private LocalDateTime inclusionDate;
    private LocalDateTime serviceStartTime;

    @Indexed(name = "checkIn.ttl.index", expireAfterSeconds = 0)
    private LocalDateTime ttl;

}