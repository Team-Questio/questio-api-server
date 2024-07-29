package team_questio.questio.portfolio.application.dto;

import team_questio.questio.portfolio.service.dto.PortfolioDetailInfo;

public record PortfolioDetailResponse(
        Long portfolioId,
        String content
) {
    public static PortfolioDetailResponse from(PortfolioDetailInfo portfolioDetailInfo) {
        return new PortfolioDetailResponse(portfolioDetailInfo.id(), portfolioDetailInfo.content());
    }
}
