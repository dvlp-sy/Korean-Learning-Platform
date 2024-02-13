package com.hacker.siyun.korlearning.dto.transcript;

import com.hacker.siyun.korlearning.model.Translation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TranslationDTO
{
    private final String countryName;
    private final String sentence;
    private final String text;

    @Builder(access = AccessLevel.PRIVATE)
    private TranslationDTO(String countryName, String sentence, String text)
    {
        this.countryName = countryName;
        this.sentence = sentence;
        this.text = text;
    }

    public static TranslationDTO build(Translation translation)
    {
        return TranslationDTO.builder()
                .countryName(translation.getCountry().getCountryName())
                .sentence(translation.getTranscript().getSentence())
                .text(translation.getText())
                .build();
    }
}
