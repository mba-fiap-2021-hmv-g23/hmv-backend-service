package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.mongodb.entity.UserEntity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserEntityMapper {

    public static UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .taxId(user.getTaxId())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .build();
    }

    public static User toUser(UserEntity entity) {
        return User.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .taxId(entity.getTaxId())
                .password(entity.getPassword())
                .fullName(entity.getFullName())
                .accessTokenExpiresIn(entity.getAccessTokenExpiresIn())
                .refreshTokenExpiresIn(entity.getRefreshTokenExpiresIn())
                .refreshToken(entity.getRefreshToken())
                .build();
    }

}