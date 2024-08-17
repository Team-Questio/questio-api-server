package team_questio.questio.security.application.dto;

public record TokenDetails(
        String accessToken,
        String refreshToken
) {
    public static TokenDetails of(String accessToken, String refreshToken) {
        return new TokenDetails(accessToken, refreshToken);
    }
}
