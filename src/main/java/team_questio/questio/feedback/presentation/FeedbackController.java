package team_questio.questio.feedback.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.feedback.application.FeedbackService;
import team_questio.questio.feedback.presentation.dto.FeedbackRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Void> createFeedback(@RequestBody FeedbackRequest request,
                                               Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getPrincipal().toString());
        feedbackService.createFeedback(userId, request.feedback());
        return ResponseEntity.ok().build();
    }
}
