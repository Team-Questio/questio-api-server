package team_questio.questio.portfolio.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_questio.questio.portfolio.persistence.QuestRepository;
import team_questio.questio.portfolio.service.command.QuestCreateCommand;

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
}
