package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.common.exception.NotFoundException;
import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import com.hacker.siyun.korlearning.common.response.SuccessMessage;
import com.hacker.siyun.korlearning.dto.*;
import com.hacker.siyun.korlearning.model.*;
import com.hacker.siyun.korlearning.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VideoService
{
    private final CategoryRepository categoryRepository;
    private final VideoRepository videoRepository;
    private final CategoryVideoRepository categoryVideoRepository;
    private final UserRepository userRepository;
    private final UserVideoRepository userVideoRepository;
    private final TranscriptRepository transcriptRepository;
    private final WebClient webClient;

    ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024))
            .build();

    public VideoService(
            CategoryRepository categoryRepository,
            VideoRepository videoRepository,
            CategoryVideoRepository categoryVideoRepository,
            UserRepository userRepository,
            UserVideoRepository userVideoRepository,
            TranscriptRepository transcriptRepository)
    {
        this.categoryRepository = categoryRepository;
        this.videoRepository = videoRepository;
        this.categoryVideoRepository = categoryVideoRepository;
        this.userRepository = userRepository;
        this.userVideoRepository = userVideoRepository;
        this.transcriptRepository = transcriptRepository;
        this.webClient = WebClient.builder().exchangeStrategies(strategies).build();
    }

    /**
     * POST API
     */

    public ApiResponse<VideoSummaryDTO> uploadVideo(String link, UserRequestDTO userRequestDTO) throws Exception
    {
        Map youtubeData;
        try {
            youtubeData = webClient.get()
                    .uri("http://localhost:8000/getYoutubeData/"+link)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        }catch(Exception e) {
            throw new Exception("An Error occured while retrieving the data");
        }

        VideoSummaryDTO videoSummaryDTO = createVideo(link, userRequestDTO.getUserId(), youtubeData);
        return ApiResponse.success(SuccessMessage.UPLOAD_VIDEO_SUCCESS, videoSummaryDTO);
    }

    private VideoSummaryDTO createVideo(String link, Long userId, Map<Long, Object> youtubeData)
    {
        Long categoryId = 0L;

        Video video = Video.builder()
                .link(link)
                .views(0L)
                .isDefault(false)
                .createdAt(new Date())
                .build();

        videoRepository.save(video);

        if (youtubeData != null && !youtubeData.isEmpty())
        {
            /* CategoryVideo */
            Object categoryIdObject = youtubeData.get("category");
            if (categoryIdObject != null)
            {
                String categoryIdString = (String)categoryIdObject;
                categoryId = Long.parseLong(categoryIdString);
            }

            /* Video */
            video.setCreator((String) youtubeData.get("creator"));
            video.setVideoTitle((String) youtubeData.get("title"));

            Object durationObject = youtubeData.get("duration");
            if (durationObject != null)
            {
                String durationString = durationObject.toString();
                video.setDuration(Long.parseLong(durationString));
            }

            Object youtubeViewsObject = youtubeData.get("youtubeViews");
            if (youtubeViewsObject != null)
            {
                String youtubeViewsString = youtubeViewsObject.toString();
                video.setYoutubeViews(Long.parseLong(youtubeViewsString));
            }

            List<Map<String, Object>> transcripts = (List<Map<String, Object>>) youtubeData.get("transcriptData");
            if (transcripts != null)
            {
                for (Map<String, Object> transcriptMap : transcripts)
                    video.getTranscripts().add(createTranscript(transcriptMap, video));

            }
        }

        createCategoryVideo(video, categoryId);
        createUserVideo(video, userId);
        videoRepository.save(video);

        return VideoSummaryDTO.build(video);
    }

    private Transcript createTranscript(Map<String, Object> transcriptMap, Video video)
    {
        Transcript transcript = Transcript.builder()
                .sentence((String) transcriptMap.get("text"))
                .start(((Number) transcriptMap.get("start")).doubleValue())
                .duration(((Number) transcriptMap.get("duration")).doubleValue())
                .video(video)
                .build();

        transcriptRepository.save(transcript);
        return transcript;
    }

    private void createCategoryVideo(Video video, Long categoryId)
    {
        CategoryVideo categoryVideo = CategoryVideo.builder()
                .video(video)
                .category(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND)))
                .build();

        categoryVideoRepository.save(categoryVideo);
    }

    private void createUserVideo(Video video, Long userId)
    {
        UserVideo userVideo = UserVideo.builder()
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .user(userRepository.findById(userId)
                            .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)))
                    .video(video)
                    .build();
        userVideoRepository.save(userVideo);

    }

    /**
     * GET API
     */

    public ApiResponse<List<VideoSummaryDTO>> getAllVideos()
    {
        List<VideoSummaryDTO> videoSummaryDTOList = videoRepository.findAll().stream()
                .map(VideoSummaryDTO::build)
                .toList();
        return ApiResponse.success(SuccessMessage.GET_ALL_VIDEOS_SUCCESS, videoSummaryDTOList);
    }

    public ApiResponse<VideoDTO> getVideoByVideoId(Long videoId)
    {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));
        return ApiResponse.success(SuccessMessage.GET_VIDEO_SUCCESS, VideoDTO.build(video));
    }

    public ApiResponse<VideosByUserDTO> getVideosByUserId(Long userId)
    {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        List<VideoSummaryDTO> videoByUserDTOList = userVideoRepository.findAllByUser_UserId(userId)
            .stream()
            .map(userVideo -> VideoSummaryDTO.build(userVideo.getVideo()))
            .toList();

        return ApiResponse.success(SuccessMessage.GET_VIDEO_SUCCESS, new VideosByUserDTO(userId, videoByUserDTOList));
    }

    public ApiResponse<List<VideoViewDTO>> getVideosSortedByRank()
    {
        List<VideoViewDTO> videoViewDTOList = videoRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Video::getViews).reversed())
                .map(VideoViewDTO::build)
                .toList();

        return ApiResponse.success(SuccessMessage.GET_VIDEO_SUCCESS, videoViewDTOList);
    }

    public ApiResponse<List<CategoryVideoDTO>> getCategoryVideos(List<Long> categoryIds)
    {
        List<CategoryVideoDTO> categoryVideoDTOList = categoryIds
                .stream()
                .map(categoryId -> {
                    List<VideoSummaryDTO> videoSummaryDTOList = categoryVideoRepository.findAllByCategory_CategoryId(categoryId)
                            .stream()
                            .map(categoryVideo -> VideoSummaryDTO.build(categoryVideo.getVideo()))
                            .toList();
                    return new CategoryVideoDTO(categoryId, videoSummaryDTOList);
                })
                .toList();

        return ApiResponse.success(SuccessMessage.GET_VIDEO_SUCCESS, categoryVideoDTOList);
    }

    /**
     * PATCH API
     */

    public ApiResponse<VideoViewDTO> patchViewByVideoId(Long videoId)
    {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));

        video.setViews(video.getViews()+1L);
        videoRepository.save(video);

        return ApiResponse.success(SuccessMessage.PATCH_VIDEO_SUCCESS, VideoViewDTO.build(video));
    }

    /**
     * DELETE API
     */

    public ApiResponse<VideoSummaryDTO> deleteVideoByVideoId(Long videoId)
    {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));

        for (Transcript transcript : video.getTranscripts())
            transcriptRepository.deleteById(transcript.getTranscriptId());

        for (CategoryVideo categoryVideo : video.getCategoryVideos())
            categoryVideoRepository.deleteById(categoryVideo.getCategoryVideoId());

        for (UserVideo userVideo : video.getUserVideos())
            userVideoRepository.deleteById(userVideo.getUserVideoId());

        videoRepository.deleteById(videoId);

        return ApiResponse.success(SuccessMessage.DELETE_VIDEO_SUCCESS);
    }

}
