package team_questio.questio.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {
    private final JWTProperties jwtProperties;

    public String generateAccessToken(Long id, String username) {
        return Jwts.builder()
                .setClaims(Map.of("id", id, "username", username))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.accessTokenExpireIn()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.accessTokenSecretKey())
                .compact();
    }

    public String generateRefreshToken(Long id, String username) {
        return Jwts.builder()
                .setClaims(Map.of("id", id, "username", username))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpireIn()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.refreshTokenSecretKey())
                .compact();
    }
}
