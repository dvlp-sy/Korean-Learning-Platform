package com.hacker.siyun.korlearning.dto.transcript;

import com.hacker.siyun.korlearning.model.Transcript;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoTranscriptDTO
{
    private final Long videoId;
    private final Long transcriptId;
    private final String sentence;
    private final Double start;
    private final Double duration;

    @Builder(access = AccessLevel.PRIVATE)
    private VideoTranscriptDTO(Long videoId, Long transcriptId, String sentence, Double start, Double duration)
    {
        this.videoId = videoId;
        this.transcriptId = transcriptId;
        this.sentence = sentence;
        this.start = start;
        this.duration = duration;
    }

    public static VideoTranscriptDTO build(Transcript transcript)
    {
        return VideoTranscriptDTO.builder()
                .videoId(transcript.getVideo().getVideoId())
                .transcriptId(transcript.getTranscriptId())
                .sentence(transcript.getSentence())
                .start(transcript.getStart())
                .duration(transcript.getDuration())
                .build();
    }
}
