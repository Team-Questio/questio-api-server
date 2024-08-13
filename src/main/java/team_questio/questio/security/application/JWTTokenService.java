package team_questio.questio.security.application;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_questio.questio.infra.RedisUtil;
import team_questio.questio.security.util.JWTTokenProvider;

@Service
@RequiredArgsConstructor
public final class JWTTokenService {
    private final JWTTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    public String generateAccessToken(PrincipleDetails principleDetails) {
        var claims = principleDetails.getClaims();

        return jwtTokenProvider.generateAccessToken(claims);
    }

    public String generateRefreshToken(PrincipleDetails principleDetails) {
        var claims = principleDetails.getClaims();
        return this.getRefreshToken(claims);
    }

    private String getRefreshToken(Map<String, Object> claims) {
        var username = (String) claims.get("username");
        var refreshToken = jwtTokenProvider.generateRefreshToken(claims);
        var key = redisUtil.getRefreshTokenPrefix(username);
        var expired = jwtTokenProvider.getRefreshTokenExpireIn();
        redisUtil.setData(key, refreshToken, expired, TimeUnit.MILLISECONDS);

        return jwtTokenProvider.generateRefreshToken(claims);
    }
}
