package team_questio.questio.portfolio.application.dto;

import java.util.List;
import team_questio.questio.portfolio.service.dto.PortfolioInfo;

public record PortfolioResponse(
        PortfolioDetailResponse portfolio,
        List<QuestDetailResponse> quests
) {
    public static PortfolioResponse from(PortfolioInfo portfolioInfo) {
        var portfolio = PortfolioDetailResponse.from(portfolioInfo.portfolio());
        var quests = portfolioInfo.quests().stream()
                .map(QuestDetailResponse::from)
                .toList();

        return new PortfolioResponse(portfolio, quests);
    }
}
