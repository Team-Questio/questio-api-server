package team_questio.questio.security.presentation.dto;

import team_questio.questio.security.application.dto.TokenDetails;

public record TokenRefreshResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenRefreshResponse from(TokenDetails tokenDetails) {
        return new TokenRefreshResponse(tokenDetails.accessToken(), tokenDetails.refreshToken());
    }
}
