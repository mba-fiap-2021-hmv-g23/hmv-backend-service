package br.com.fiap.hmv.infra.rest.api.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostRefreshTokenResponse {

    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime expiresIn;

}