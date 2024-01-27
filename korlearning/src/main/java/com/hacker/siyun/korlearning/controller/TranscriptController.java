package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.TranscriptsDTO;
import com.hacker.siyun.korlearning.model.Transcript;
import com.hacker.siyun.korlearning.service.TranscriptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranscriptController
{
    private final TranscriptService transcriptService;

    public TranscriptController(TranscriptService transcriptService) { this.transcriptService = transcriptService; }

    @GetMapping("/videos/{videoId}/transcripts")
    public ApiResponse<TranscriptsDTO> getAllTranscripts(@PathVariable("videoId") Long videoId)
    {
        return transcriptService.getAllTranscripts(videoId);
    }
}
