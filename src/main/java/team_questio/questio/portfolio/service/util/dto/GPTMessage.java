package team_questio.questio.portfolio.service.util.dto;

public record GPTMessage(
        String role,
        String content
) {
    public GPTMessage(String content) {
        this("user", content);
    }
}
