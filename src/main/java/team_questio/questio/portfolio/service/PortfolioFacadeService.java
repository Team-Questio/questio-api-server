package team_questio.questio.portfolio.service;

import lombok.RequiredArgsConstructor;
import team_questio.questio.common.annotation.Facade;
import team_questio.questio.gpt.service.GPTService;
import team_questio.questio.gpt.service.dto.GptParam;
import team_questio.questio.portfolio.service.dto.PortfolioInfo;
import team_questio.questio.portfolio.service.dto.PortfolioParam;

@Facade
@RequiredArgsConstructor
public class PortfolioFacadeService {
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

    public PortfolioInfo getPortfolio(Long portfolioId) {
        var portfolioDetail = portfolioService.getPortfolio(portfolioId);
        var questInfos = questService.getQuests(portfolioId);

        return PortfolioInfo.from(portfolioDetail, questInfos);
    }
}
