package br.com.fiap.hmv.application.port;

import br.com.fiap.hmv.domain.entity.Patient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PatientPort {

    Mono<Void> insert(final Patient patient);

    Mono<Patient> findById(String patientId);

    Mono<Patient> getByTaxId(String patientTaxId);

    Flux<Patient> search();

}