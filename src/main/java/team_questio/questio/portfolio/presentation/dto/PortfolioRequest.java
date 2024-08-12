package team_questio.questio.portfolio.presentation.dto;

import team_questio.questio.portfolio.application.dto.PortfolioParam;

public record PortfolioRequest(
        String content
) {
    public PortfolioParam toPortfolioParam() {
        return new PortfolioParam(content());
    }
}
