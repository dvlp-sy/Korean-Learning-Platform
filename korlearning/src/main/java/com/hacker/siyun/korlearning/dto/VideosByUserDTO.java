package com.hacker.siyun.korlearning.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class VideosByUserDTO
{
    private final Long userId;
    private final List<VideoSummaryDTO> videoList;

    public VideosByUserDTO(Long userId, List<VideoSummaryDTO> videoSummaryDTOList)
    {
        this.userId = userId;
        this.videoList = videoSummaryDTOList;
    }

}
