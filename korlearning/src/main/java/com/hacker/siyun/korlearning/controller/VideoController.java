package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.UserRequestDTO;
import com.hacker.siyun.korlearning.dto.VideoSummaryDTO;
import com.hacker.siyun.korlearning.service.VideoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController
{
    private final VideoService videoService;

    public VideoController(VideoService videoService) { this.videoService = videoService; }

    @PostMapping("/videos")
    public ApiResponse<VideoSummaryDTO> uploadVideo(@RequestParam(name = "link") String link, @RequestBody UserRequestDTO userRequestDTO) throws Exception
    {
        return videoService.uploadVideo(link, userRequestDTO);
    }
}
