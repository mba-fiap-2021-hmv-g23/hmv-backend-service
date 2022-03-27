package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.AttendancePort;
import br.com.fiap.hmv.application.port.CheckInPort;
import br.com.fiap.hmv.application.port.PatientPort;
import br.com.fiap.hmv.domain.entity.AttendanceQueueCalls;
import br.com.fiap.hmv.domain.entity.CheckIn;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceAppService {

    private final AttendancePort attendancePort;
    private final CheckInPort checkInPort;
    private final PatientPort patientPort;

    public Mono<Void> startAttendanceService(String userTaxId) {
        log.info("[APPLICATION_SERVICE] Iniciando jornada do usuário de atendimento à pacientes.");
        return attendancePort.startServiceToPatient(userTaxId);
    }

    public Mono<Void> stopAttendanceService(String userTaxId) {
        log.info("[APPLICATION_SERVICE] Encerrando jornada do usuário de atendimento à pacientes.");
        return attendancePort.stopServiceToPatient(userTaxId);
    }

    public Mono<CheckIn> nextPatientToAttendance(String userId) {
        log.info("[APPLICATION_SERVICE] Iniciando chamada ao próximo paciente aguardando atendimento.");
        return checkInPort.findNextAwaitingAttendance(userId).flatMap(checkIn -> {
            checkIn.setAttendant(User.builder().userId(userId).build());
            if (checkIn.getCalls() == 3) {
                checkIn.setCalls(0);
                checkIn.setNoShows(checkIn.getNoShows() + 1);
                checkIn.setServiceStartBaseDate(checkIn.getServiceStartBaseDate().plusMinutes(checkIn.getNoShows()));
                checkIn.setReservedAttendantDate(now());
                return checkInPort.updateStartAttendance(checkIn).thenReturn(checkIn)
                        .flatMap(u -> nextPatientToAttendance(userId));
            } else {
                checkIn.setReservedAttendantDate(now().plusSeconds(60));
                checkIn.setCalls(checkIn.getCalls() + 1);
                checkIn.setLastCallDate(now());
                return checkInPort.updateStartAttendance(checkIn).thenReturn(checkIn);
            }
        }).flatMap(checkIn -> patientPort.get(checkIn.getPatient().getPatientId()).flatMap(patient -> {
            checkIn.setPatient(patient);
            return Mono.just(checkIn);
        }));
    }

    public Mono<AttendanceQueueCalls> findQueueCalls() {
        log.info("[APPLICATION_SERVICE] Iniciando busca da fila de atendimentos à pacientes.");
        return checkInPort.findAwaitingAttendance()
                .flatMap(checkIn -> patientPort.get(
                        checkIn.getPatient().getPatientId()
                ).flatMap(patient -> {
                    checkIn.setPatient(patient);
                    return Mono.just(checkIn);
                })).collectList()
                .flatMap(this::buildAttendanceQueueCalls);
    }

    public Mono<Void> startAttendanceToPatient(String checkInId, String userId) {
        return Mono.empty();
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