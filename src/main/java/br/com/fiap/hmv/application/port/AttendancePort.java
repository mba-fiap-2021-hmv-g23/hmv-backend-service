package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.AttendanceService;
import reactor.core.publisher.Mono;

public interface AttendancePort {

    Mono<Void> startAttendanceService(AttendanceService attendanceService);

    Mono<Void> stopAttendanceService(String attendanceId);

}