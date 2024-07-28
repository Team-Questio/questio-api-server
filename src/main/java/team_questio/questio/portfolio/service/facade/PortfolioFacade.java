package team_questio.questio.portfolio.service.facade;

import lombok.RequiredArgsConstructor;
import team_questio.questio.common.annotation.Facade;
import team_questio.questio.gpt.service.GPTService;
import team_questio.questio.gpt.service.dto.GptParam;
import team_questio.questio.portfolio.service.PortfolioService;
import team_questio.questio.portfolio.service.QuestService;
import team_questio.questio.portfolio.service.dto.PortfolioParam;

@Facade
@RequiredArgsConstructor
public class PortfolioFacade {
    private final PortfolioService portfolioService;
    private final GPTService gptService;
    private final QuestService questService;

    public Long createPortfolio(PortfolioParam portfolioParam) {
        var portfolioId = portfolioService.createPortfolio(portfolioParam);
        var questions = gptService.generateQuestion(GptParam.of(portfolioParam.content()));

        var questCreateCommands = questions.stream()
                .map(question -> question.toCommand(portfolioId))
                .toList();

        questService.createQuests(questCreateCommands);
        return portfolioId;
    }
}
