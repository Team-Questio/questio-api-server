package team_questio.questio.portfolio.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import team_questio.questio.portfolio.presentation.dto.FeedbackRequest;
import team_questio.questio.portfolio.presentation.dto.GenerationResponse;
import team_questio.questio.portfolio.presentation.dto.PortfolioRequest;
import team_questio.questio.portfolio.presentation.dto.PortfolioResponse;

@Tag(name = "포트폴리오 담당 API", description = "포트폴리오 정보를 담당합니다.")
public interface PortfolioApiController {

    @Operation(summary = "포트폴리오를 생성합니다.", description = "포트폴리오를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "포트폴리오 생성 성공"),
            @ApiResponse(responseCode = "G001", description = "포트폴리오 컨텐츠가 CS와 관련이 없다고 판단되었습니다."),
            @ApiResponse(responseCode = "G002", description = "질문 JSON 파싱 중 오류가 발생했습니다."),
            @ApiResponse(responseCode = "G003", description = "질문 생성 중 오류가 발생했습니다.")
    })
    ResponseEntity<GenerationResponse> createPortfolio(PortfolioRequest request, Authentication authentication);


    @Operation(summary = "포트폴리오를 조회합니다.", description = "나의 포트폴리오를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포트폴리오 조회 성공"),
            @ApiResponse(responseCode = "P001", description = "포트폴리오를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "P002", description = "포트폴리오에 접근할 수 없습니다.")
    })
    ResponseEntity<PortfolioResponse> getPortfolio(Long portfolioId, Authentication authentication);

    @Operation(summary = "평가를 전송합니다.", description = "평가를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "평가 전송 성공"),
            @ApiResponse(responseCode = "P003", description = "일치하는 질문을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "P004", description = "적절하지 않은 피드백입니다.")
    })
    ResponseEntity<Void> updateFeedback(Long questId, FeedbackRequest request);

    @Operation(summary = "포트폴리오 목록을 조회합니다.", description = "나의 포트폴리오 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포트폴리오 목록 조회 성공")
    })
    ResponseEntity<List<PortfolioResponse>> getPortfolios(Authentication authentication);
}
