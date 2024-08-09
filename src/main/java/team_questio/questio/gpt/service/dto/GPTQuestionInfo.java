package team_questio.questio.gpt.service.dto;

import team_questio.questio.portfolio.application.command.QuestCreateCommand;

public record GPTQuestionInfo(
        String question,
        String answer
) {
    public static GPTQuestionInfo of(String question, String answer) {
        return new GPTQuestionInfo(question, answer);
    }

    public QuestCreateCommand toCommand(Long portfolioId) {
        return QuestCreateCommand.of(question, answer, portfolioId);
    }
}
