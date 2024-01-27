package com.hacker.siyun.korlearning.dto.transcript;

import com.hacker.siyun.korlearning.model.Transcript;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TranscriptDTO
{
    private final Long transcriptId;
    private final String sentence;
    private final Double start;
    private final Double duration;

    @Builder(access = AccessLevel.PRIVATE)
    private TranscriptDTO(Long transcriptId, String sentence, Double start, Double duration)
    {
        this.transcriptId = transcriptId;
        this.sentence = sentence;
        this.start = start;
        this.duration = duration;
    }

    public static TranscriptDTO build(Transcript transcript)
    {
        return TranscriptDTO.builder()
                .transcriptId(transcript.getTranscriptId())
                .sentence(transcript.getSentence())
                .start(transcript.getStart())
                .duration(transcript.getDuration())
                .build();
    }
}
