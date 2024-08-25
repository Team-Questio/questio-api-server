package team_questio.questio.portfolio.application.command;

import team_questio.questio.portfolio.domain.Portfolio;

public record PortfolioCommand(
        Long userId,
        String content
) {
    public Portfolio toEntity() {
        return new Portfolio(userId, content);
    }
}
