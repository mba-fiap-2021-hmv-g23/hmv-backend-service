package br.com.fiap.hmv.config;

import br.com.fiap.hmv.security.AuthenticationManager;
import br.com.fiap.hmv.security.ServerSecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityFilters {

    private final AuthenticationManager authenticationManager;
    private final ServerSecurityContext serverSecurityContext;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .cors().disable()
                .csrf().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(serverSecurityContext)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(
                        HttpMethod.GET,
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/webjars/**",
                        "/swagger-ui/**"
                ).permitAll()
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
                .pathMatchers(HttpMethod.POST, "/refresh-token").permitAll()
                .pathMatchers(HttpMethod.POST, "/users").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

}