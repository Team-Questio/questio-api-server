package team_questio.questio.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {
    private final JWTProperties jwtProperties;

    public String generateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject((String) claims.get("username"))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now())
                .setExpiration(expirationWith(JWTProperties::accessTokenExpireIn))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.accessTokenSecretKey())
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject((String) claims.get("username"))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now())
                .setExpiration(expirationWith(JWTProperties::refreshTokenExpireIn))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.refreshTokenSecretKey())
                .compact();
    }

    public Map<String, Object> getAccessClaims(String token) {
        return getClaim(token, claims -> claims, JWTProperties::accessTokenSecretKey);
    }

    public boolean isInvalidAccessToken(String token) {
        return Objects.isNull(getSubjectFromToken(token, JWTProperties::accessTokenSecretKey))
                || isTokenExpired(token, JWTProperties::accessTokenSecretKey);
    }

    public boolean isInvalidRefreshToken(String token) {
        return Objects.isNull(getSubjectFromToken(token, JWTProperties::refreshTokenSecretKey))
                || isTokenExpired(token, JWTProperties::refreshTokenSecretKey);
    }

    public String getSubjectFromToken(String token, Function<JWTProperties, String> secretResolver) {
        return getClaim(token, Claims::getSubject, secretResolver);
    }

    private boolean isTokenExpired(String token, Function<JWTProperties, String> secretResolver) {
        return getClaim(token, Claims::getExpiration, secretResolver)
                .before(now());
    }

    private <T> T getClaim(String token,
                           Function<Claims, T> claimsResolver,
                           Function<JWTProperties, String> secretResolver
    ) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secretResolver.apply(jwtProperties))
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Date now() {
        return new Date(System.currentTimeMillis());
    }

    private Date expirationWith(Function<JWTProperties, Long> expiration) {
        return new Date(System.currentTimeMillis() + expiration.apply(jwtProperties));
    }

    public long getRefreshTokenExpireIn() {
        return jwtProperties.refreshTokenExpireIn();
    }
}
