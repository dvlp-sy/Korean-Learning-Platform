package com.hacker.siyun.korlearning.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryVideoDTO
{
    private final Long categoryId;
    private final List<VideoSummaryDTO> videoList;

    public CategoryVideoDTO(Long categoryId, List<VideoSummaryDTO> videoList)
    {
        this.categoryId = categoryId;
        this.videoList = videoList;
    }
}
