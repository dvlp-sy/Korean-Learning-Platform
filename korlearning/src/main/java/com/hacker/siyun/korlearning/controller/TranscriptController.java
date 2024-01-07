package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.service.TranscriptService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranscriptController
{
    private final TranscriptService transcriptService;

    public TranscriptController(TranscriptService transcriptService) { this.transcriptService = transcriptService; }
}
