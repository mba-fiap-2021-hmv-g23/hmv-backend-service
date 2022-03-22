package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttendanceAdapter implements AttendancePort {

    @Override
    public Mono<Void> startService(String attendantTaxTd) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do usuário no serviço de atendimento na base de dados.");
        return Mono.empty();
    }

    @Override
    public Mono<Void> closeService(String attendantTaxTd) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando exclusão do usuário no serviço de atendimento na base de dados.");
        return Mono.empty();
    }

    @Override
    public Mono<List<User>> findAttendantsInService() {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando busca dos usuários em serviço de atendimento base de dados.");
        return Mono.just(List.of());
    }

}