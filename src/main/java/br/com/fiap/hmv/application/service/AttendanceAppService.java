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

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceAppService {

    private final AttendancePort attendancePort;
    private final PatientPort patientPort;
    private final CheckInPort checkInPort;

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
        return checkInPort.findAwaitingAttendance().collectList().flatMap(awaitingAttendanceList -> {
            Optional<CheckIn> checkInOpt = awaitingAttendanceList.stream().findFirst();
            if (checkInOpt.isPresent()) {
                CheckIn checkIn = checkInOpt.get();
                return patientPort.getByTaxId(checkIn.getPatient().getTaxId()).flatMap(patient -> {
                    checkIn.setPatient(patient);
                    checkIn.setAttendant(User.builder().userId(userId).build());
                    return attendancePort.insertCallToStartAttendance(checkIn).thenReturn(checkIn);
                });
            }
            return Mono.empty();
        });
    }

    public Mono<AttendanceQueueCalls> findQueueCalls() {
        return Mono.just(AttendanceQueueCalls.builder().build());
    }

}