package team_questio.questio.security.application;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.AuthError;
import team_questio.questio.infra.RedisUtil;
import team_questio.questio.security.application.dto.PrincipleDetails;
import team_questio.questio.security.application.dto.TokenDetails;
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

    public TokenDetails reissueAccessToken(String refreshToken) {
        if (isInvalidateRefreshToken(refreshToken)) {
            throw QuestioException.of(AuthError.INVALID_REFRESH_TOKEN);
        }

        var claims = jwtTokenProvider.getRefreshClaims(refreshToken);
        var newAccessToken = jwtTokenProvider.generateAccessToken(claims);
        var newRefreshToken = this.getRefreshToken(claims);

        return TokenDetails.of(newAccessToken, newRefreshToken);
    }

    private String getRefreshToken(Map<String, Object> claims) {
        var username = (String) claims.get("username");
        var refreshToken = jwtTokenProvider.generateRefreshToken(claims);
        var key = redisUtil.getRefreshTokenPrefix(username);
        var expired = jwtTokenProvider.getRefreshTokenExpireIn();
        redisUtil.setData(key, refreshToken, expired, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    private boolean isInvalidateRefreshToken(String refreshToken) {
        return jwtTokenProvider.isInvalidRefreshToken(refreshToken) || isOldRefreshToken(refreshToken);
    }

    private boolean isOldRefreshToken(String refreshToken) {
        var name = (String) jwtTokenProvider.getRefreshClaims(refreshToken).get("username");
        var key = redisUtil.getRefreshTokenPrefix(name);
        var currentRefreshToken = redisUtil.getData(key, String.class)
                .orElseThrow(() -> QuestioException.of(AuthError.INVALID_REFRESH_TOKEN));

        return !currentRefreshToken.equals(refreshToken);
    }
}
