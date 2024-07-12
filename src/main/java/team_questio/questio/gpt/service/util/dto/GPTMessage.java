package team_questio.questio.gpt.service.util.dto;

public record GPTMessage(
        String role,
        String content
) {
    public GPTMessage(String content) {
        this("user", content);
    }
}
