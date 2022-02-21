package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.mongodb.entity.UserEntity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserEntityMapper {

    public static UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .taxId(user.getTaxId())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .build();
    }

    public static User toUser(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .taxId(entity.getTaxId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .fullName(entity.getFullName())
                .accessTokenExpiresIn(entity.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(entity.getRefreshTokenExpiresIn())
                .refreshToken(entity.getRefreshToken())
                .build();
    }
}
