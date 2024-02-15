package com.hacker.siyun.korlearning.service;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;
import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.common.exception.NotFoundException;
import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import com.hacker.siyun.korlearning.common.response.SuccessMessage;
import com.hacker.siyun.korlearning.dto.transcript.TranscriptDTO;
import com.hacker.siyun.korlearning.dto.transcript.TranscriptsDTO;
import com.hacker.siyun.korlearning.dto.transcript.TranslationDTO;
import com.hacker.siyun.korlearning.dto.transcript.VideoTranscriptDTO;
import com.hacker.siyun.korlearning.model.Country;
import com.hacker.siyun.korlearning.model.Transcript;
import com.hacker.siyun.korlearning.model.Translation;
import com.hacker.siyun.korlearning.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TranscriptService
{
    private final VideoRepository videoRepository;
    private final CountryRepository countryRepository;
    private final TranscriptRepository transcriptRepository;
    private final TranslationRepository translationRepository;

    @Value("${spring.deepl.key}")
    private String authKey;


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

        return ApiResponse.success(SuccessMessage.GET_ALL_TRANSCRIPTS_SUCCESS, new TranscriptsDTO(videoId, transcriptList));
    }

    public ApiResponse<VideoTranscriptDTO> getTranscript(Long videoId, Long transcriptId)
    {
        videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));

        Transcript transcript = transcriptRepository.findById(transcriptId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TRANSCRIPT_NOT_FOUND));

        if (!videoId.equals(transcript.getVideo().getVideoId()))
            throw new NotFoundException(ErrorMessage.VIDEO_TRANSCRIPT_NOT_FOUND);

        return ApiResponse.success(SuccessMessage.GET_TRANSCRIPT_SUCCESS, VideoTranscriptDTO.build(transcript));
    }

    public ApiResponse<TranslationDTO> getTranslationByCountryId(Long videoId, Long transcriptId, Long countryId) throws DeepLException, InterruptedException
    {
        /* IF TRANSLATION ALREADY EXISTS */
        Optional<Translation> optionalTranslation =  translationRepository.findByTranscript_TranscriptId(transcriptId);
        if (optionalTranslation.isPresent())
        {
            Translation translation = optionalTranslation.get();
            return ApiResponse.success(SuccessMessage.GET_TRANSLATION_SUCCESS, new TranslationDTO(translation.getCountry().getCountryName(), translation.getTranscript().getSentence(), translation.getText()));
        }

        /* EXCEPTION */
        videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));

        Transcript transcript = transcriptRepository.findById(transcriptId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TRANSCRIPT_NOT_FOUND));

        if (!videoId.equals(transcript.getVideo().getVideoId()))
            throw new NotFoundException(ErrorMessage.VIDEO_TRANSCRIPT_NOT_FOUND);

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COUNTRY_NOT_FOUND));

        /* GET TEXT USING DEEPL */
        Translator translator;
        String authKey = this.authKey;
        translator = new Translator(authKey);
        String text = translator.translateText(transcript.getSentence(), null, country.getCountryCode()).getText();

        /* SAVE TRANSLATION */
        saveTranslation(transcript, country, text);

        return ApiResponse.success(SuccessMessage.GET_TRANSLATION_SUCCESS, new TranslationDTO(country.getCountryName(), transcript.getSentence(), text));
    }

    public void saveTranslation(Transcript transcript, Country country, String text)
    {
        Translation translation = Translation.builder()
                .transcript(transcript)
                .country(country)
                .text(text)
                .build();

        translationRepository.save(translation);
    }

}
