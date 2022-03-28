package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.application.port.UserPort;
import br.com.fiap.hmv.domain.entity.AttendanceQueueCalls;
import br.com.fiap.hmv.domain.entity.AttendanceService;
import br.com.fiap.hmv.domain.entity.CheckIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static br.com.fiap.hmv.domain.type.EstimatedTimeArrival.ESTA_NO_LOCAL;
import static java.time.LocalDateTime.now;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceAppService {

    private final AttendancePort attendancePort;
    private final CheckInPort checkInPort;
    private final UserPort userPort;

    public Mono<Void> startAttendanceService(String serviceDesk, String attendantId) {
        log.info("[APPLICATION_SERVICE] Iniciando jornada do usuário de atendimento à pacientes.");
        return userPort.findById(attendantId)
                .flatMap(user -> attendancePort.startAttendanceService(AttendanceService.builder()
                        .serviceDesk(serviceDesk)
                        .attendant(user)
                        .build())).then();
    }

    public Mono<Void> stopAttendanceService(String attendantId) {
        log.info("[APPLICATION_SERVICE] Encerrando jornada do usuário de atendimento à pacientes.");
        return attendancePort.stopAttendanceService(attendantId);
    }

    public Mono<CheckIn> nextPatientToAttendance(String attendantId) {
        log.info("[APPLICATION_SERVICE] Iniciando chamada ao próximo paciente aguardando atendimento.");
        return attendancePort.findByAttendantId(attendantId)
                .flatMap(attendanceService -> checkInPort.findNextAwaitingAttendance(attendantId).flatMap(checkIn -> {
                    checkIn.setAttendant(attendanceService.getAttendant());
                    checkIn.setServiceDesk(attendanceService.getServiceDesk());
                    if (checkIn.getCalls() == 3) {
                        checkIn.setCalls(0);
                        checkIn.setNoShows(checkIn.getNoShows() + 1);
                        checkIn.setServiceStartBaseDate(checkIn.getServiceStartBaseDate()
                                .plusMinutes(checkIn.getNoShows()));
                        checkIn.setReservedAttendantDate(now());
                        return checkInPort.updateStartAttendance(checkIn).thenReturn(checkIn)
                                .flatMap(u -> nextPatientToAttendance(attendantId));
                    } else {
                        checkIn.setReservedAttendantDate(now().plusSeconds(60));
                        checkIn.setCalls(checkIn.getCalls() + 1);
                        checkIn.setLastCallDate(now());
                        return checkInPort.updateStartAttendance(checkIn).thenReturn(checkIn);
                    }
                }));
    }

    public Mono<AttendanceQueueCalls> findQueueCalls() {
        log.info("[APPLICATION_SERVICE] Iniciando busca da fila de atendimentos à pacientes.");
        return checkInPort.findAwaitingAttendance().collectList().flatMap(this::buildAttendanceQueueCalls);
    }

    public Mono<Void> startAttendanceToPatient(String checkInId, String attendantId) {
        log.info("[APPLICATION_SERVICE] Iniciando atendimento à pacientes.");
        return attendancePort.findByAttendantId(attendantId)
                .flatMap(attendanceService -> checkInPort.findById(checkInId).flatMap(checkIn -> {
                    checkIn.setEstimatedTimeArrival(ESTA_NO_LOCAL);
                    checkIn.setAttendant(attendanceService.getAttendant());
                    checkIn.setServiceDesk(attendanceService.getServiceDesk());
                    return checkInPort.startAttendanceToPatient(checkIn);
                }));
    }

    private Mono<AttendanceQueueCalls> buildAttendanceQueueCalls(List<CheckIn> awaitingAttendanceList) {
        return Mono.just(AttendanceQueueCalls.builder()
                .inCall(awaitingAttendanceList.stream()
                        .sorted(comparing(CheckIn::getServiceStartBaseDate))
                        .filter(this::filterInCall).collect(toList()))
                .lastCalls(awaitingAttendanceList.stream()
                        .sorted(comparing(CheckIn::getServiceStartBaseDate))
                        .filter(this::filterLastCalls)
                        .limit(10)
                        .collect(toList()))
                .awaitingCall(awaitingAttendanceList.stream()
                        .sorted(comparing(CheckIn::getServiceStartBaseDate))
                        .filter(this::filterAwaitingCall).collect(toList()))
                .build());
    }

    private boolean filterInCall(CheckIn checkIn) {
        return checkIn.getCalls() >= 1;
    }

    private boolean filterLastCalls(CheckIn checkIn) {
        return checkIn.getNoShows() >= 1 && checkIn.getCalls() == 0;
    }

    private boolean filterAwaitingCall(CheckIn checkIn) {
        return checkIn.getCalls() == 0;
    }

}