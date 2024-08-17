package team_questio.questio.portfolio.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import team_questio.questio.portfolio.presentation.dto.FeedbackRequest;
import team_questio.questio.portfolio.presentation.dto.PortfolioRequest;
import team_questio.questio.portfolio.presentation.dto.PortfolioResponse;

@Tag(name = "포트폴리오 담당 API", description = "포트폴리오 정보를 담당합니다.")
public interface PortfolioApiController {

    @Operation(summary = "포트폴리오를 생성합니다.", description = "포트폴리오를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "포트폴리오 생성 성공")
    })
    ResponseEntity<Void> createPortfolio(PortfolioRequest request);


    @Operation(summary = "포트폴리오를 조회합니다.", description = "포트폴리오를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포트폴리오 조회 성공")
    })
    ResponseEntity<PortfolioResponse> getPortfolio(Long portfolioId);

    @Operation(summary = "평가를 전송합니다.", description = "평가를 전송합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "평가 전송 성공")
    })
    ResponseEntity<Void> updateFeedback(Long questId, FeedbackRequest request);
}
