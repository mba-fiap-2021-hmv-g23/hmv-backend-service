package br.com.fiap.hmv.security;

import br.com.fiap.hmv.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Slf4j
@Service("jwtService")
public class JwtService {

    private static final String CUSTOM_CLAIM_USERNAME = "username";
    private static final String CUSTOM_CLAIM_TAX_ID = "taxId";
    private static final String CUSTOM_CLAIM_FULL_NAME = "fullName";
    private static final String CUSTOM_CLAIM_EXPIRES_IN = "expiresIn";

    @Value("${auth.jwt.secretKey:MY_SECRET_KEY}")
    private String secretKey;


    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim(CUSTOM_CLAIM_EXPIRES_IN, user.getAccessTokenExpiresIn().format(ISO_LOCAL_DATE_TIME))
                .claim(CUSTOM_CLAIM_USERNAME, user.getUsername())
                .claim(CUSTOM_CLAIM_TAX_ID, user.getTaxId())
                .claim(CUSTOM_CLAIM_FULL_NAME, user.getFullName())
                .signWith(HS512, secretKey)
                .compact();
    }

    public String getUsername(String accessToken) {
        return getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_USERNAME, String.class);
    }

    public String getTaxId(String accessToken) {
        return getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_TAX_ID, String.class);
    }

    public String getFullName(String accessToken) {
        return getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_FULL_NAME, String.class);

    }

    public LocalDateTime getExpiresIn(String accessToken) {
        return parse(
                getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_EXPIRES_IN, String.class),
                ISO_LOCAL_DATE_TIME
        );
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

}