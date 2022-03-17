package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.UserPort;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAppService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserPort userPort;

    public Mono<Void> insert(final User user) {
        log.info("[APPLICATION_SERVICE] Iniciando o cadastro de usuário. Nome de usuário: {}.", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userPort.insert(user).doOnSuccess(v -> log.info("[APPLICATION_SERVICE] Usuário cadastrado com " +
                "sucesso. ID do usuário: {}, Nome de usuário: {}.", user.getId(), user.getUsername()
        ));
    }

}