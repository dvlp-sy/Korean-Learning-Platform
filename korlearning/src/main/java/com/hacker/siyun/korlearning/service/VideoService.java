package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.repository.*;
import org.springframework.stereotype.Service;

@Service
public class VideoService
{
    private final CategoryRepository categoryRepository;
    private final VideoRepository videoRepository;
    private final CategoryVideoRepository categoryVideoRepository;
    private final UserRepository userRepository;
    private final UserVideoRepository userVideoRepository;
    private final TranscriptRepository transcriptRepository;

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
    }

}
