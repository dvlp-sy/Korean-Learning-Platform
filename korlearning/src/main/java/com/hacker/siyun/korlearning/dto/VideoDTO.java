package com.hacker.siyun.korlearning.dto;

import com.hacker.siyun.korlearning.model.Video;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class VideoDTO
{
    private final Long videoId;
    private final String link;
    private final String videoTitle;
    private final Long duration;
    private final String creator;
    private final Date createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private VideoDTO(Long videoId, String link, String videoTitle, Long duration, String creator, Date createdAt)
    {
        this.videoId = videoId;
        this.link = link;
        this.videoTitle = videoTitle;
        this.duration = duration;
        this.creator = creator;
        this.createdAt = createdAt;
    }

    public static VideoDTO build(Video video)
    {
        return VideoDTO.builder()
                .videoId(video.getVideoId())
                .link(video.getLink())
                .videoTitle(video.getVideoTitle())
                .duration(video.getDuration())
                .creator(video.getCreator())
                .createdAt(video.getCreatedAt())
                .build();
    }
}
