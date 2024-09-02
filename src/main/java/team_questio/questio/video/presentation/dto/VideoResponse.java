package team_questio.questio.video.presentation.dto;

import team_questio.questio.video.application.dto.VideoInfo;

public record VideoResponse(
        String title,
        String url
) {
    public static VideoResponse from(VideoInfo videoInfo) {
        return new VideoResponse(videoInfo.title(), videoInfo.url());
    }
}