package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckInAppService {

    private final CheckInPort checkInPort;

    public Mono<Void> checkIn(final CheckIn checkIn) {
        log.info("[APPLICATION_SERVICE] Iniciando check-in do paciente.");
        return checkInPort.insert(checkIn);
    }

}