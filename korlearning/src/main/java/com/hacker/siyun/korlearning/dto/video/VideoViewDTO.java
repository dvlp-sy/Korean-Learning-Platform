package com.hacker.siyun.korlearning.dto.video;

import com.hacker.siyun.korlearning.model.Video;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoViewDTO
{
    private final Long videoId;
    private final String videoTitle;
    private final Long views;

    @Builder(access = AccessLevel.PRIVATE)
    private VideoViewDTO(Long videoId, String videoTitle, Long views)
    {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.views = views;
    }

    public static VideoViewDTO build(Video video)
    {
        return VideoViewDTO.builder()
                .videoId(video.getVideoId())
                .videoTitle(video.getVideoTitle())
                .views(video.getViews())
                .build();
    }
}
