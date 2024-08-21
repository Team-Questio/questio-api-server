package team_questio.questio.portfolio.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.portfolio.domain.Feedback;
import team_questio.questio.common.exception.code.PortfolioError;
import team_questio.questio.portfolio.persistence.QuestRepository;
import team_questio.questio.portfolio.application.command.QuestCreateCommand;
import team_questio.questio.portfolio.application.dto.QuestDetailInfo;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;

    public void createQuests(List<QuestCreateCommand> questCreateCommands) {
        var quests = questCreateCommands.stream()
                .map(QuestCreateCommand::toEntity)
                .toList();
        questRepository.saveAll(quests);
    }

    public List<QuestDetailInfo> getQuests(Long portfolioId) {
        var quests = questRepository.findByPortfolioId(portfolioId);

        var response = quests.stream()
                .map(QuestDetailInfo::from)
                .toList();
        return response;
    }

    public void updateFeedback(Long id, Integer feedback) {
        var quest = questRepository.findById(id)
            .orElseThrow(() -> QuestioException.of(PortfolioError.QUEST_NOT_FOUND));
        quest.updateFeedback(Feedback.of(feedback));
    }
}
