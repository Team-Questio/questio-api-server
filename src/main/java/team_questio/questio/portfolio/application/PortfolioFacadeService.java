package team_questio.questio.portfolio.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import team_questio.questio.common.annotation.Facade;
import team_questio.questio.gpt.service.GPTService;
import team_questio.questio.gpt.service.dto.GptParam;
import team_questio.questio.portfolio.application.command.PortfolioCommand;
import team_questio.questio.portfolio.application.dto.GenerationInfo;
import team_questio.questio.portfolio.application.dto.PortfolioInfo;
import team_questio.questio.user.application.UserService;

@Facade
@RequiredArgsConstructor
public class PortfolioFacadeService {
    private final PortfolioService portfolioService;
    private final GPTService gptService;
    private final QuestService questService;
    private final UserService userService;

    public GenerationInfo createPortfolio(PortfolioCommand portfolioCommand) {
        var remaining = userService.count(portfolioCommand.userId());

        var portfolioId = portfolioService.createPortfolio(portfolioCommand);
        var questions = gptService.generateQuestion(GptParam.of(portfolioCommand.content()));

        var questCreateCommands = questions.stream()
                .map(question -> question.toCommand(portfolioId))
                .toList();

        questService.createQuests(questCreateCommands);
        return GenerationInfo.of(portfolioId, remaining);
    }

    public PortfolioInfo getPortfolio(Long portfolioId, Long userId) {
        var portfolioDetail = portfolioService.getPortfolio(portfolioId, userId);
        var questInfos = questService.getQuests(portfolioId);

        return PortfolioInfo.from(portfolioDetail, questInfos);
    }

    @Transactional
    public void updateFeedback(Long questId, Integer feedback) {
        questService.updateFeedback(questId, feedback);
    }

    public List<PortfolioInfo> getPortfolios(Long userId) {
        var portfolioDetails = portfolioService.getPortfolios(userId);
        var response = portfolioDetails.stream()
                .map(portfolioDetail -> {
                    var questInfos = questService.getQuests(portfolioDetail.id());
                    return PortfolioInfo.from(portfolioDetail, questInfos);
                })
                .toList();

        return response;
    }
}
