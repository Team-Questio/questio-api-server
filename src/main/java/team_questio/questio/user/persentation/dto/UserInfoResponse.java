package team_questio.questio.user.persentation.dto;

public record UserInfoResponse(
        String username
) {
    public static UserInfoResponse of(String username) {
        return new UserInfoResponse(username);
    }
}
