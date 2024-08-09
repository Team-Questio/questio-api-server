package team_questio.questio.portfolio.application.dto;

import team_questio.questio.portfolio.domain.Portfolio;

public record PortfolioDetailInfo(
        Long id,
        String content
) {
    public static PortfolioDetailInfo from(Portfolio portfolio) {
        return new PortfolioDetailInfo(portfolio.getId(), portfolio.getContent());
    }
}
