package team_questio.questio.video.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_questio.questio.video.application.VideoService;
import team_questio.questio.video.presentation.dto.VideoResponse;


@RequestMapping("/api/v1/videos")
@RestController
@RequiredArgsConstructor
public class VideoController implements VideoApiController {
    private final VideoService videoService;

    @Override
    @GetMapping("/random")
    public ResponseEntity<VideoResponse> getRandomVideo() {
        var videoInfo = videoService.findByRandom();

        var response = VideoResponse.from(videoInfo);
        return ResponseEntity.ok()
                .body(response);
    }
}
