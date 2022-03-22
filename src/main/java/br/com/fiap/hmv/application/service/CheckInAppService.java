package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.domain.entity.CheckIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckInAppService {

    public Mono<Void> checkIn(final CheckIn checkIn) {
        return Mono.empty();
    }
}
