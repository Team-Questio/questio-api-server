package team_questio.questio.video.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import team_questio.questio.video.presentation.dto.VideoResponse;

@Tag(name = "테크 튜브 API", description = "CS 관련 영상 정보를 담당합니다.")
public interface VideoApiController {

    @Operation(method = "GET", summary = "랜덤 영상을 조회합니다.", description = "랜덤 영상을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "랜덤 영상 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VideoResponse.class))),
            @ApiResponse(responseCode = "V001", description = "영상이 존재하지 않음",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<VideoResponse> getRandomVideo();
}
