package team_questio.questio.user.persentation.dto;

public record RemainingResponse(
    Integer remaining
) {

    public static RemainingResponse of(Integer remaining) {
        return new RemainingResponse(remaining);
    }
}
