package team_questio.questio.portfolio.presentation.dto;

import team_questio.questio.portfolio.application.dto.GenerationInfo;

public record GenerationResponse(
    Long portfolioId,
    Integer remaining
) {

    public static GenerationResponse from(GenerationInfo generationInfo) {
        return new GenerationResponse(generationInfo.portfolioId(),generationInfo.remaining());
    }
}
