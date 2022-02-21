package br.com.fiap.hmv.security;

import br.com.fiap.hmv.application.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static br.com.fiap.hmv.application.utils.ObfuscateUtils.obfuscateToken;

@Slf4j
@RequiredArgsConstructor
@Component("authenticationManager")
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String accessToken = authentication.getCredentials().toString();
        log.info("[SECURITY] Iniciando autenticação do usuário. Token de acesso: {}", obfuscateToken(accessToken));
        LocalDateTime expiresIn = jwtService.getExpiresIn(accessToken);
        if (LocalDateTime.now().isAfter(expiresIn)) {
            return Mono.error(new UnauthorizedException("Sessão expirada."));
        }
        return Mono.just(new UsernamePasswordAuthenticationToken(
                jwtService.getUsername(accessToken),
                authentication.getCredentials(),
                authentication.getAuthorities())
        );
    }

}