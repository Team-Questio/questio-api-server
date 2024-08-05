package team_questio.questio.portfolio.application.dto;

import java.util.List;

public record PortfolioInfo(
        PortfolioDetailInfo portfolio,
        List<QuestDetailInfo> quests
) {
    public static PortfolioInfo from(PortfolioDetailInfo portfolioDetail, List<QuestDetailInfo> questInfos) {
        return new PortfolioInfo(portfolioDetail, questInfos);
    }
}
