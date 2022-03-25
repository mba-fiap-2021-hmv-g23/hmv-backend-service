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

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceAppService {

    private final AttendancePort attendancePort;
    private final CheckInPort checkInPort;
    private final PatientPort patientPort;

    public Mono<Void> startServiceToPatient(String userTaxId) {
        log.info("[APPLICATION_SERVICE] Iniciando jornada do usuário de atendimento à pacientes.");
        return attendancePort.startServiceToPatient(userTaxId);
    }

    public Mono<Void> stopServiceToPatient(String userTaxId) {
        log.info("[APPLICATION_SERVICE] Encerrando jornada do usuário de atendimento à pacientes.");
        return attendancePort.stopServiceToPatient(userTaxId);
    }

    public Mono<CheckIn> nextPatientToAttendance(String userId) {
        log.info("[APPLICATION_SERVICE] Iniciando chamada ao próximo paciente aguardando atendimento.");
        return checkInPort.findNextAwaitingAttendance(userId).flatMap(checkIn -> {
            checkIn.setAttendant(User.builder().userId(userId).build());
            return patientPort.get(checkIn.getPatient().getPatientId()).flatMap(patient -> {
                checkIn.setPatient(patient);
                return attendancePort.insertCallToStartAttendance(checkIn).thenReturn(checkIn);
            });
        });
    }

    public Mono<AttendanceQueueCalls> findQueueCalls() {
        return checkInPort.findAwaitingAttendance()
                .flatMap(checkIn -> patientPort.get(checkIn.getPatient().getPatientId()).flatMap(patient -> {
                    checkIn.setPatient(patient);
                    return Mono.just(checkIn);
                }))
                .collectList()
                .flatMap(awaitingAttendanceList -> Mono.just(
                        AttendanceQueueCalls.builder()
                                .awaitingCall(awaitingAttendanceList.stream()
                                        .sorted(comparing(CheckIn::getServiceStartBaseDate))
                                        .collect(toList()))
                                .build()));
    }

}