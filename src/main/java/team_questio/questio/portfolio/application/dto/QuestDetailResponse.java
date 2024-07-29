package team_questio.questio.portfolio.application.dto;

import team_questio.questio.portfolio.service.dto.QuestDetailInfo;

public record QuestDetailResponse(
        Long questId,
        String question
) {
    public static QuestDetailResponse from(QuestDetailInfo questDetailInfo) {
        return new QuestDetailResponse(questDetailInfo.id(), questDetailInfo.question());
    }
}
