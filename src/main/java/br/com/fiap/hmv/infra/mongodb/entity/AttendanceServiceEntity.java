package br.com.fiap.hmv.infra.mongodb.entity;

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
@Document(collection = "attendanceService")
public class AttendanceServiceEntity {

    @Transient
    public static final long TTL_MINUTES = 60 * 24 * 7;

    @Id
    private String attendanceServiceId;

    private String userTaxId;
    private LocalDateTime startDate;

    @Indexed(name = "attendanceService.ticketWindow.unique-index", unique = true)
    private String ticketWindow;

    @Indexed(name = "attendanceService.ttl.index", expireAfterSeconds = 0)
    private LocalDateTime endDate;

}