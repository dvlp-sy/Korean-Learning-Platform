package com.hacker.siyun.korlearning.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TranscriptsDTO
{
    private final Long videoId;
    private final List<TranscriptDTO> transcriptList;

    public TranscriptsDTO(Long videoId, List<TranscriptDTO> transcriptList)
    {
        this.videoId = videoId;
        this.transcriptList = transcriptList;
    }
}
