package team_questio.questio.feedback.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team_questio.questio.feedback.domain.Feedback;
import team_questio.questio.feedback.persistence.FeedbackRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void createFeedback(Long userId, String content) {
        var feedback = Feedback.of(userId, content);
        feedbackRepository.save(feedback);
    }
}
