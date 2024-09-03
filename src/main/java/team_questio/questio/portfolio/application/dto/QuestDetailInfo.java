package team_questio.questio.portfolio.application.dto;

import team_questio.questio.portfolio.domain.Feedback;
import team_questio.questio.portfolio.domain.Quest;

public record QuestDetailInfo(
        Long id,
        String question,
        String answer,
        Feedback feedback
) {
    public static QuestDetailInfo from(Quest quest) {
        return new QuestDetailInfo(
                quest.getId(),
                quest.getQuestion(),
                quest.getAnswer(),
                quest.getFeedback()
        );
    }
}
