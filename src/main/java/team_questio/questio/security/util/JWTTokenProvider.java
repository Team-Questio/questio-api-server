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


    public Map<String, Object> getClaims(String token) {
        return getClaimFromAccessToken(token, claims -> claims);
    }

    public boolean isInvalidToken(String token) {
        return Objects.isNull(getSubjectFromToken(token)) || isTokenExpired(token);
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromAccessToken(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return getClaimFromAccessToken(token, Claims::getExpiration)
                .before(now());
    }

    private <T> T getClaimFromAccessToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.accessTokenSecretKey())
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
}
