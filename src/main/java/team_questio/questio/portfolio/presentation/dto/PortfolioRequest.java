package team_questio.questio.portfolio.presentation.dto;

import team_questio.questio.portfolio.application.command.PortfolioCommand;

public record PortfolioRequest(
        String content
) {
    public PortfolioCommand toPortfolioParam(final Long userId) {
        return new PortfolioCommand(userId, content());
    }
}
