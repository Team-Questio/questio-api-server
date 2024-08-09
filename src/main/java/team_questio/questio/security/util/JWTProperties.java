package team_questio.questio.security.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JWTProperties(
        String accessTokenSecretKey,
        String refreshTokenSecretKey,
        Long accessTokenExpireIn,
        Long refreshTokenExpireIn
) {
}
