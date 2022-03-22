package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static br.com.fiap.hmv.domain.service.CheckInService.calculateServiceQueueTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckInAppService {

    private final CheckInPort checkInPort;
    private final AttendancePort attendancePort;

    public Mono<Void> checkIn(final CheckIn checkIn) {
        log.info("[APPLICATION_SERVICE] Iniciando check-in do paciente.");
        return checkInPort.insert(checkIn).switchIfEmpty(Mono.defer(() -> Mono.zip(
                        checkInPort.findAwaitingAttendance().collectList(),
                        attendancePort.findAttendantsInService().collectList())
                .flatMap(tuple2 -> {
                    List<CheckIn> awaitingAttendance = tuple2.getT1();
                    List<User> attendantsInService = tuple2.getT2();
                    calculateServiceQueueTime(awaitingAttendance, attendantsInService, checkIn);
                    return checkInPort.update(checkIn);
                })));
    }

}