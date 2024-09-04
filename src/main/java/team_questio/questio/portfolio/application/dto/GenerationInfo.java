package team_questio.questio.portfolio.application.dto;

public record GenerationInfo(
    Long portfolioId,
    Integer remaining
) {

    public static GenerationInfo of(Long portfolioId, Integer remaining) {
        return new GenerationInfo(portfolioId, remaining);
    }
}
