package com.hacker.siyun.korlearning.dto.transcript;

import lombok.Getter;

@Getter
public class TranslationDTO
{
    private final String countryName;
    private final String sentence;
    private final String text;

    public TranslationDTO(String countryName, String sentence, String text)
    {
        this.countryName = countryName;
        this.sentence = sentence;
        this.text = text;
    }
}
