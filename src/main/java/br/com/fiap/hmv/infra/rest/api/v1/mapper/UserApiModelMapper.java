package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.User;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostLoginRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostLoginResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostRefreshTokenRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostRefreshTokenResponse;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostUserRequest;
import br.com.fiap.hmv.infra.rest.api.v1.model.PostUserResponse;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserApiModelMapper {

    private final static Pattern REGEX_TAX_ID = Pattern.compile("^\\d{11}$|(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)");

    public static User toUser(PostUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(request.getPassword())
                .taxId(request.getTaxId().replaceAll("\\.|\\-", ""))
                .build();
    }

    public static User toUser(PostLoginRequest request) {
        String username = null;
        String taxId = null;
        if (REGEX_TAX_ID.matcher(request.getLogin()).matches()) {
            taxId = request.getLogin().replaceAll("\\.|\\-", "");
        } else {
            username = request.getLogin();
        }
        return User.builder()
                .username(username)
                .taxId(taxId)
                .password(request.getPassword())
                .build();
    }

    public static User toUser(PostRefreshTokenRequest request) {
        return User.builder()
                .refreshToken(request.getRefreshToken())
                .build();
    }


    public static PostUserResponse toPostUserResponse(User user) {
        return PostUserResponse.builder()
                .userId(user.getUserId())
                .build();
    }

    public static PostLoginResponse toPostLoginResponse(User user) {
        return PostLoginResponse.builder()
                .accessToken(user.getAccessToken())
                .refreshToken(user.getRefreshToken())
                .username(user.getUsername())
                .taxId(user.getTaxId())
                .fullName(user.getFullName())
                .expiresIn(user.getAccessTokenExpiresIn())
                .build();
    }

    public static PostRefreshTokenResponse toPostRefreshTokenResponse(User user) {
        return PostRefreshTokenResponse.builder()
                .accessToken(user.getAccessToken())
                .refreshToken(user.getRefreshToken())
                .expiresIn(user.getAccessTokenExpiresIn())
                .build();
    }

}