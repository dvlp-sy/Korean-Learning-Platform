package com.hacker.siyun.korlearning.service;

import com.hacker.siyun.korlearning.repository.*;
import org.springframework.stereotype.Service;

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
}
