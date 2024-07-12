package team_questio.questio.gpt.service.dto;

public record GptParam(
        Long portfolioId,
        String portfolio
) {
    public static GptParam of(Long portfolioId, String portfolio) {
        return new GptParam(portfolioId, portfolio);
    }
}
