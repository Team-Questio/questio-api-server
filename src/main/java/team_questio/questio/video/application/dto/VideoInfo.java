package team_questio.questio.video.application.dto;

import team_questio.questio.video.domain.Video;

public record VideoInfo(
        String title,
        String url
) {
    public static VideoInfo from(Video video) {
        return new VideoInfo(video.getTitle(), video.getUrl());
    }
}
