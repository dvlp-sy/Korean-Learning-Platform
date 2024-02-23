package com.hacker.siyun.korlearning.service;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;
import com.hacker.siyun.korlearning.common.ApiResponse;
import com.hacker.siyun.korlearning.common.exception.NotFoundException;
import com.hacker.siyun.korlearning.common.response.ErrorMessage;
import com.hacker.siyun.korlearning.common.response.SuccessMessage;
import com.hacker.siyun.korlearning.dto.transcript.*;
import com.hacker.siyun.korlearning.model.Country;
import com.hacker.siyun.korlearning.model.Transcript;
import com.hacker.siyun.korlearning.model.Translation;
import com.hacker.siyun.korlearning.repository.CountryRepository;
import com.hacker.siyun.korlearning.repository.TranscriptRepository;
import com.hacker.siyun.korlearning.repository.TranslationRepository;
import com.hacker.siyun.korlearning.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TranscriptService
{
    private final VideoRepository videoRepository;
    private final CountryRepository countryRepository;
    private final TranscriptRepository transcriptRepository;
    private final TranslationRepository translationRepository;
    private final WebClient webClient;

    @Value("${spring.deepl.key}")
    private String authKey;

    ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024))
            .build();

    public TranscriptService(
            VideoRepository videoRepository,
            CountryRepository countryRepository,
            TranscriptRepository transcriptRepository,
            TranslationRepository translationRepository
    )
    {
        this.videoRepository = videoRepository;
        this.countryRepository = countryRepository;
        this.transcriptRepository = transcriptRepository;
        this.translationRepository = translationRepository;
        this.webClient = WebClient.builder().exchangeStrategies(strategies).build();
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

    public ApiResponse<List<WordsExamplesDTO>> getWordsExamples(Long videoId, Long transcriptId) throws Exception
    {
        /* EXCEPTION */
        videoRepository.findById(videoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VIDEO_NOT_FOUND));

        Transcript transcript = transcriptRepository.findById(transcriptId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TRANSCRIPT_NOT_FOUND));

        if (!videoId.equals(transcript.getVideo().getVideoId()))
            throw new NotFoundException(ErrorMessage.VIDEO_TRANSCRIPT_NOT_FOUND);

        /* GET FROM FLASK */
        Map wordsData;
        try {
            wordsData = webClient.get()
                    .uri("http://localhost:8000/getWords/"+transcript.getSentence())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        }catch(Exception e) {
            throw new Exception("An Error occured while retrieving the data");
        }

        List<WordsExamplesDTO> wordsExamplesDTOList = new ArrayList<>();
        if (wordsData != null && !wordsData.isEmpty())
        {
            List<Map<String, Object>> words = (List<Map<String, Object>>) wordsData.get("wordsData");
            if (words != null && !words.isEmpty())
            {
                for (Map<String, Object> wordMap : words)
                {
                    String word = (String) wordMap.get("word");
                    List<String> exampleList = (List<String>) wordMap.get("examples");
                    wordsExamplesDTOList.add(new WordsExamplesDTO(word, exampleList));
                }
            }
        }

        if (wordsExamplesDTOList.isEmpty())
            throw new NotFoundException(ErrorMessage.WORD_NOT_FOUND);

        return ApiResponse.success(SuccessMessage.GET_WORD_SUCCESS, wordsExamplesDTOList);
    }

}
