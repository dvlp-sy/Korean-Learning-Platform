package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.UserRequestDTO;
import com.hacker.siyun.korlearning.dto.VideoDTO;
import com.hacker.siyun.korlearning.dto.VideoSummaryDTO;
import com.hacker.siyun.korlearning.dto.VideoViewPatchDTO;
import com.hacker.siyun.korlearning.service.VideoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/videos")
    public ApiResponse<List<VideoSummaryDTO>> getAllVideos()
    {
        return videoService.getAllVideos();
    }

    @GetMapping("/videos/{videoId}")
    public ApiResponse<VideoDTO> getVideoByVideoId(@PathVariable("videoId") Long videoId)
    {
        return videoService.getVideoByVideoId(videoId);
    }

    @PatchMapping("/videos/{videoId}")
    public ApiResponse<VideoViewPatchDTO> patchViewByVideoId(@PathVariable("videoId") Long videoId)
    {
        return videoService.patchViewByVideoId(videoId);
    }
}
