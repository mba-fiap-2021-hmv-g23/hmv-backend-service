package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.CheckIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CheckInPort {

    Mono<Void> insert(final CheckIn checkIn);

    Mono<Void> update(final CheckIn checkIn);

    Mono<Void> updateStartAttendance(CheckIn checkIn);

    Mono<Void> startAttendanceToPatient(CheckIn checkIn);

    Mono<CheckIn> findById(String checkInId);

    Mono<CheckIn> findNextAwaitingAttendance(String attendanceId);

    Flux<CheckIn> findAwaitingAttendance();

}