package com.hacker.siyun.korlearning.dto;

import com.hacker.siyun.korlearning.model.Video;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoSummaryDTO
{
    private final Long videoId;
    private final String link;
    private final String videoTitle;

    @Builder(access = AccessLevel.PRIVATE)
    private VideoSummaryDTO(Long videoId, String link, String videoTitle)
    {
        this.videoId = videoId;
        this.link = link;
        this.videoTitle = videoTitle;
    }

    public static VideoSummaryDTO build(Video video)
    {
        return VideoSummaryDTO.builder()
                .videoId(video.getVideoId())
                .link(video.getLink())
                .videoTitle(video.getVideoTitle())
                .build();
    }
}
