package br.com.fiap.hmv.infra.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "user")
public class UserEntity {

    @Id
    private String userId;

    @Indexed(name = "user.username.unique-index", unique = true)
    private String username;

    @Indexed(name = "user.taxId.unique-index", unique = true)
    private String taxId;

    private String fullName;
    private String password;
    private String refreshToken;
    private LocalDateTime accessTokenExpiresIn;
    private LocalDateTime refreshTokenExpiresIn;

}