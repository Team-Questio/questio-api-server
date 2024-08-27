package team_questio.questio.feedback.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import team_questio.questio.feedback.presentation.dto.FeedbackRequest;

@Tag(name = "사이트 피드백 API", description = "사이트 피드백을 담당합니다.")
public interface FeedbackApiController {

    @Operation(summary = "피드백을 전송합니다.", description = "사이트 피드백을 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "피드백 전송 성공")
    })
    ResponseEntity<Void> createFeedback(FeedbackRequest request, Authentication authentication);
}
