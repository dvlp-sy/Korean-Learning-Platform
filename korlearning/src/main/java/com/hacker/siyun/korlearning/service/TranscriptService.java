package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.common.exception.NotFoundException;
import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import com.hacker.siyun.korlearning.common.response.SuccessMessage;
import com.hacker.siyun.korlearning.dto.TranscriptDTO;
import com.hacker.siyun.korlearning.dto.TranscriptsDTO;
import com.hacker.siyun.korlearning.model.Transcript;
import com.hacker.siyun.korlearning.model.Video;
import com.hacker.siyun.korlearning.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranscriptService
{
    private final VideoRepository videoRepository;
    private final CountryRepository countryRepository;
    private final TranscriptRepository transcriptRepository;
    private final TranslationRepository translationRepository;
    private final WordRepository wordRepository;
    private final ExampleRepository exampleRepository;

    public TranscriptService(
            VideoRepository videoRepository,
            CountryRepository countryRepository,
            TranscriptRepository transcriptRepository,
            TranslationRepository translationRepository,
            WordRepository wordRepository,
            ExampleRepository exampleRepository
    )
    {
        this.videoRepository = videoRepository;
        this.countryRepository = countryRepository;
        this.transcriptRepository = transcriptRepository;
        this.translationRepository = translationRepository;
        this.wordRepository = wordRepository;
        this.exampleRepository = exampleRepository;
    }

    public ApiResponse<TranscriptsDTO> getAllTranscripts(Long videoId)
    {
        videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));

        List<TranscriptDTO> transcriptList = transcriptRepository.findAllByVideo_VideoId(videoId)
                .stream()
                .map(TranscriptDTO::build)
                .toList();

        if (transcriptList.isEmpty())
            throw new NotFoundException(ErrorMessage.TRANSCRIPT_NOT_FOUND);

        return ApiResponse.success(SuccessMessage.GET_TRANSCRIPTS_SUCCESS, new TranscriptsDTO(videoId, transcriptList));
    }
}
