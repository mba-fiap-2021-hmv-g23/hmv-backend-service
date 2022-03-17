package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.PatientPort;
import br.com.fiap.hmv.domain.entity.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientAppService {

    private final PatientPort patientPort;

    public Mono<Void> insert(Patient patient) {
        log.info("[APPLICATION_SERVICE] Iniciando o cadastro do paciente");
        return patientPort.insert(patient);
    }

    public Mono<Patient> search(String patientId) {
        log.info("[APPLICATION_SERVICE] Iniciando a busca do paciente");
        return patientPort.get(patientId);
    }

    public Flux<Patient> search() {
        log.info("[APPLICATION_SERVICE] Iniciando a busca dos pacientes");
        return patientPort.search();
    }
    
}