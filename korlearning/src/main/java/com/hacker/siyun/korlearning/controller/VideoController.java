package com.hacker.siyun.korlearning.controller;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.dto.*;
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

    @GetMapping("/videos/users/{userId}")
    public ApiResponse<VideosByUserDTO> getVideosByUserId(@PathVariable("userId") Long userId)
    {
        return videoService.getVideosByUserId(userId);
    }

    @GetMapping("/videos/rank")
    public ApiResponse<List<VideoViewDTO>> getVideosSortedByRank()
    {
        return videoService.getVideosSortedByRank();
    }

    @GetMapping("/videos/categories")
    public ApiResponse<List<CategoryVideoDTO>> getCategoryVideos(@RequestParam("categoryId") List<Long> categoryIds)
    {
        return videoService.getCategoryVideos(categoryIds);
    }

    @PatchMapping("/videos/{videoId}")
    public ApiResponse<VideoViewDTO> patchViewByVideoId(@PathVariable("videoId") Long videoId)
    {
        return videoService.patchViewByVideoId(videoId);
    }

    @DeleteMapping("/videos/{videoId}")
    public ApiResponse<VideoSummaryDTO> deleteVideoByVideoId(@PathVariable("videoId") Long videoId)
    {
        return videoService.deleteVideoByVideoId(videoId);
    }
}
