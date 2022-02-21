package br.com.fiap.hmv.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class User {

    private String id;
    private String username;
    private String taxId;
    private String email;
    private String fullName;
    private String password;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiresIn;
    private LocalDateTime refreshTokenExpiresIn;

}