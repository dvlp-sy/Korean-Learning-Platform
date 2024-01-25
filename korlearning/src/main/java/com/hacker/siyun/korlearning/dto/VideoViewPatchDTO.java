package com.hacker.siyun.korlearning.dto;

import com.hacker.siyun.korlearning.model.Video;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoViewPatchDTO
{
    private final Long videoId;
    private final Long views;

    @Builder
    private VideoViewPatchDTO(Long videoId, Long views)
    {
        this.videoId = videoId;
        this.views = views;
    }

    public static VideoViewPatchDTO build(Video video)
    {
        return VideoViewPatchDTO.builder()
                .videoId(video.getVideoId())
                .views(video.getViews())
                .build();
    }
}
