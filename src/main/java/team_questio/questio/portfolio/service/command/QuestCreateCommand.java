package team_questio.questio.portfolio.service.command;

import team_questio.questio.portfolio.domain.Quest;

public record QuestCreateCommand(
        String question,
        String answer,
        Long portfolioId
) {
    public static QuestCreateCommand of(String question, String answer, Long portfolioId) {
        return new QuestCreateCommand(question, answer, portfolioId);
    }

    public Quest toEntity() {
        return Quest.builder()
                .question(question)
                .answer(answer)
                .portfolioId(portfolioId)
                .build();
    }
}
