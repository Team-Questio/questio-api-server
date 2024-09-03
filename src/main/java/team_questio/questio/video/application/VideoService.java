package team_questio.questio.video.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_questio.questio.common.exception.QuestioException;
import team_questio.questio.common.exception.code.VideoError;
import team_questio.questio.video.application.dto.VideoInfo;
import team_questio.questio.video.persistence.VideoRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoInfo findByRandom() {
        var video = videoRepository.findByRandom()
                .orElseThrow(() -> QuestioException.of(VideoError.VIDEO_NOT_FOUND));

        return VideoInfo.from(video);
    }
}
