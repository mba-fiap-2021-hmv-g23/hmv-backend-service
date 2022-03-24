package br.com.fiap.hmv.security;

import br.com.fiap.hmv.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Slf4j
@Service("jwtService")
public class JwtService {

    private static final String CUSTOM_CLAIM_USER_TAX_ID = "userTaxId";
    private static final String CUSTOM_CLAIM_PATIENT_ID = "patientId";
    private static final String CUSTOM_CLAIM_EXPIRES_IN = "expiresIn";

    @Value("${auth.jwt.secretKey:MY_SECRET_KEY}")
    private String secretKey;


    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim(CUSTOM_CLAIM_USER_TAX_ID, user.getTaxId())
                .claim(CUSTOM_CLAIM_PATIENT_ID, user.getPatientId())
                .claim(CUSTOM_CLAIM_EXPIRES_IN, user.getAccessTokenExpiresIn().format(ISO_LOCAL_DATE_TIME))
                .signWith(HS512, secretKey)
                .compact();
    }

    public String getUserId(String accessToken) {
        return getAllClaimsFromToken(accessToken).getSubject();
    }

    public String getPatientId(String accessToken) {
        return getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_PATIENT_ID, String.class);
    }

    public String getUserTaxId(String accessToken) {
        return getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_USER_TAX_ID, String.class);
    }

    public LocalDateTime getExpiresIn(String accessToken) {
        return parse(
                getAllClaimsFromToken(accessToken).get(CUSTOM_CLAIM_EXPIRES_IN, String.class),
                ISO_LOCAL_DATE_TIME
        );
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

}