package com.hacker.siyun.korlearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Table
@Entity
public class Transcript
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transcript_id")
    private Long transcriptId;

    @Column
    private String sentence;

    @Column
    private Double start;

    @Column
    private Double duration;

    @Column
    private String soundLink;

    @OneToMany(mappedBy = "transcript", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "transcript", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Translation> translations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    @JsonBackReference
    private Video video;

    @Builder
    public Transcript(String sentence, Double start, Double duration, String soundLink, Video video)
    {
        this.sentence = sentence;
        this.start = start;
        this.duration = duration;
        this.soundLink = soundLink;
        this.video = video;
    }

}
