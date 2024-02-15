package com.hacker.siyun.korlearning.controller;

import com.deepl.api.DeepLException;
import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.transcript.TranscriptsDTO;
import com.hacker.siyun.korlearning.dto.transcript.TranslationDTO;
import com.hacker.siyun.korlearning.dto.transcript.VideoTranscriptDTO;
import com.hacker.siyun.korlearning.service.TranscriptService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/videos/{videoId}/transcripts/{transcriptId}")
    public ApiResponse<VideoTranscriptDTO> getTranscript(@PathVariable("videoId") Long videoId, @PathVariable("transcriptId") Long transcriptId)
    {
        return transcriptService.getTranscript(videoId, transcriptId);
    }

    @GetMapping("/videos/{videoId}/transcripts/{transcriptId}/countries/{countryId}/translation")
    public ApiResponse<TranslationDTO> getTranslationByCountryId(@PathVariable("videoId") Long videoId, @PathVariable("transcriptId") Long transcriptId, @PathVariable("countryId") Long countryId) throws DeepLException, InterruptedException {
        return transcriptService.getTranslationByCountryId(videoId, transcriptId, countryId);
    }
}
