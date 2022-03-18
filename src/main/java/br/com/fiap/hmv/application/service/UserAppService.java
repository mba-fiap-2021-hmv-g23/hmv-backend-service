package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.UserPort;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAppService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserPort userPort;

    public Mono<Void> insert(final User user) {
        log.info("[APPLICATION_SERVICE] Iniciando o cadastro de usuário.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (isNull(user.getUsername())) {
            user.setUsername(user.getFullName().replaceAll(" ", "") + user.getTaxId());
        }
        return userPort.insert(user);
    }

}