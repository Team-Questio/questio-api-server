package team_questio.questio.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {
    private final JWTProperties jwtProperties;

    public String generateAccessToken(Long id, String username, List<String> roles) {
        return Jwts.builder()
                .setClaims(Map.of("id", id, "username", username, "roles", roles))
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.accessTokenExpireIn()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.accessTokenSecretKey())
                .compact();
    }

    public String generateRefreshToken(Long id, String username) {
        return Jwts.builder()
                .setClaims(Map.of("id", id, "username", username))
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpireIn()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.refreshTokenSecretKey())
                .compact();
    }

    public Map<String, Object> getClaims(String token) {
        return getClaimFromAccessToken(token, claims -> claims);
    }

    public boolean isInvalidToken(String token) {
        return getSubjectFromToken(token) == null || isTokenExpired(token);
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromAccessToken(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return getClaimFromAccessToken(token, Claims::getExpiration).before(new Date());
    }

    private <T> T getClaimFromAccessToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.accessTokenSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
