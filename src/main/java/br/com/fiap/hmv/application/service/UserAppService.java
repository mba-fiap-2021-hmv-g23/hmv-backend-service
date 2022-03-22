package br.com.fiap.hmv.application.service;

import br.com.fiap.hmv.application.port.UserPort;
import br.com.fiap.hmv.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Locale.ROOT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.stripAccents;

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
            String fullName = stripAccents(user.getFullName().trim().toLowerCase(ROOT));
            user.setUsername(fullName.replaceAll(" ", ".") + user.getTaxId());
        }
        return userPort.insert(user);
    }

}