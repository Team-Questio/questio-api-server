package team_questio.questio.gpt.service.dto;

public record GptParam(
        String portfolio
) {
    public static GptParam of(String portfolio) {
        return new GptParam(portfolio);
    }
}
