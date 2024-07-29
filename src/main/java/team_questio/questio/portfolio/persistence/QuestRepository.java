package team_questio.questio.portfolio.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team_questio.questio.portfolio.domain.Quest;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByPortfolioId(Long portfolioId);
}
