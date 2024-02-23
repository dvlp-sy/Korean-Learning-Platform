package com.hacker.siyun.korlearning.dto.transcript;

import lombok.Getter;

import java.util.List;

@Getter
public class WordsExamplesDTO
{
    private final String word;
    private final List<String> exampleList;

    public WordsExamplesDTO(String word, List<String> exampleList)
    {
        this.word = word;
        this.exampleList = exampleList;
    }
}
