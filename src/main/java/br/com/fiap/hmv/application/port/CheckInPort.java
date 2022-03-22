package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.CheckIn;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CheckInPort {

    Mono<Void> insert(final CheckIn checkIn);

    Mono<Void> update(final CheckIn checkIn);

    Mono<List<CheckIn>> findAwaitingAttendance();

}