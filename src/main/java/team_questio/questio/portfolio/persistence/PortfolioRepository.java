package team_questio.questio.portfolio.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team_questio.questio.portfolio.domain.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUserId(Long userId);
}
