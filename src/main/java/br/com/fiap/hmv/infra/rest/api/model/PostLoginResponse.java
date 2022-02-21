package br.com.fiap.hmv.infra.rest.api.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostLoginResponse {

    private final String accessToken;
    private final String refreshToken;
    private final String fullName;
    private final String taxId;
    private final String username;
    private final LocalDateTime expiresIn;

}