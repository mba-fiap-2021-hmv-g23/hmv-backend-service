package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.domain.entity.AttendanceService;
import br.com.fiap.hmv.infra.mongodb.entity.AttendanceServiceEntity;
import br.com.fiap.hmv.infra.mongodb.repository.AttendanceServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;

@Slf4j
@RequiredArgsConstructor
@Component
public class AttendanceAdapter implements AttendancePort {

    private final AttendanceServiceRepository attendanceServiceRepository;

    @Override
    public Mono<Void> startAttendanceService(AttendanceService attendanceService) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do usuário em serviço de atendimento na base de dados.");
        return attendanceServiceRepository.deleteByAttendantId(attendanceService.getAttendant().getUserId())
                .switchIfEmpty(Mono.defer(() -> attendanceServiceRepository.save(AttendanceServiceEntity.builder()
                        .serviceDesk(attendanceService.getServiceDesk())
                        .attendantId(attendanceService.getAttendant().getUserId())
                        .attendantFullName(attendanceService.getAttendant().getFullName())
                        .startDate(now())
                        .build()
                ).then()));
    }

    @Override
    public Mono<Void> stopAttendanceService(String attendantId) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando atualização de encerramento de serviço de atendimento " +
                "na base de dados.");
        return attendanceServiceRepository.deleteByAttendantId(attendantId);
    }

}