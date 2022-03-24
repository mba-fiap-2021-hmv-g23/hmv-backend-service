package br.com.fiap.hmv.infra.mongodb.entity;

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
@Document(collection = "checkInCallService")
public class CheckInCallServiceEntity {

    @Id
    private String chekInId;

    private String patientId;
    private String userId;
    private Integer calls;
    private String noShows;
    private LocalDateTime inclusionDate;
    private LocalDateTime updateDate;
    private LocalDateTime blockCallUntilDate;

    @Indexed(name = "checkInCallService.ttl.index", expireAfterSeconds = 0)
    private LocalDateTime ttl;

}