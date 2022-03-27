package br.com.fiap.hmv.infra.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "attendanceService")
public class AttendanceServiceEntity {

    @Id
    private String serviceDesk;

    private String attendantId;
    private String attendantFullName;
    private LocalDateTime startDate;

}