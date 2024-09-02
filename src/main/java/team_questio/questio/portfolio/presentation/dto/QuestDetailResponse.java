package team_questio.questio.portfolio.presentation.dto;

import team_questio.questio.portfolio.application.dto.QuestDetailInfo;
import team_questio.questio.portfolio.domain.Feedback;

public record QuestDetailResponse(
        Long questId,
        String question,
        Feedback feedback
) {
    public static QuestDetailResponse from(QuestDetailInfo questDetailInfo) {
        return new QuestDetailResponse(
                questDetailInfo.id(),
                questDetailInfo.question(),
                questDetailInfo.feedback()
        );
    }
}
