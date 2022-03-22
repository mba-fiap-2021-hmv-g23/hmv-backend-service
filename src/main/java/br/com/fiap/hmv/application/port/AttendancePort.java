package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AttendancePort {

    Mono<Void> startService(String attendantTaxTd);

    Mono<Void> closeService(String attendantTaxTd);

    Mono<List<User>> findAttendantsInService();

}
