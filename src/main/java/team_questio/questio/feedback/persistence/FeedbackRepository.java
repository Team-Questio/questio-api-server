package team_questio.questio.feedback.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import team_questio.questio.feedback.domain.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
