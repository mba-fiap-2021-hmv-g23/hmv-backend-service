package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttendancePort {

    Mono<Void> startService(String attendantTaxTd);

    Mono<Void> closeService(String attendantTaxTd);

    Flux<User> findAttendantsInService();

}
