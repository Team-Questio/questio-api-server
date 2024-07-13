package team_questio.questio.portfolio.service.dto;

import team_questio.questio.portfolio.domain.Portfolio;

public record PortfolioParam(
        String content
) {
    public Portfolio toEntity() {
        return new Portfolio(content);
    }
}
