package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.service.VideoService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController
{
    private final VideoService videoService;

    public VideoController(VideoService videoService) { this.videoService = videoService; }
}
