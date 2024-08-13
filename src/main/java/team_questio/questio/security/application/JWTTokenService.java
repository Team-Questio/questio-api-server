package team_questio.questio.security.application;

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

        return jwtTokenProvider.generateRefreshToken(claims);
    }
}
