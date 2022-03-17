package br.com.fiap.hmv.infra.mongodb.adapter;

import br.com.fiap.hmv.application.port.PatientPort;
import br.com.fiap.hmv.domain.entity.Patient;
import br.com.fiap.hmv.infra.mongodb.mapper.PatientEntityMapper;
import br.com.fiap.hmv.infra.mongodb.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.infra.mongodb.mapper.PatientEntityMapper.toPatientEntity;
import static java.util.UUID.randomUUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class PatientAdapter implements PatientPort {

    private final PatientRepository patientRepository;

    @Override
    public Mono<Void> insert(Patient patient) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando inclusão do paciente na base de dados.");
        patient.setPatientId(randomUUID().toString());
        return patientRepository.insert(toPatientEntity(patient)).then();
    }

    @Override
    public Mono<Patient> get(String patientId) {
        log.info("[INFRA_MONGODB_ADAPTER] Iniciando busca do usuário na base de dados.");
        return patientRepository.findById(patientId).map(PatientEntityMapper::toPatient);
    }

}