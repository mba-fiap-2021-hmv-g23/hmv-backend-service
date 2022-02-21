package br.com.fiap.hmv.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.com.fiap.hmv.application.utils.ObfuscateUtils.obfuscateToken;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
@Component("securityContext")
public class ServerSecurityContext implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        String header = httpRequest.getHeaders().getFirst(AUTHORIZATION);
        if (nonNull(header)) {
            String token = header.replace("Bearer ", "");
            log.info("[SECURITY] Header AUTHORIZATION localizado. Token de acesso: {}", obfuscateToken(token));
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
                    .map(SecurityContextImpl::new);
        }
        return Mono.error(new RuntimeException("Acesso não autorizado."));
    }

}