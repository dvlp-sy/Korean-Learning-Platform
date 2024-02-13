package com.hacker.siyun.korlearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Table
@Entity
public class Translation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private Long translationId;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "transcript_id", nullable = false)
    @JsonBackReference
    private Transcript transcript;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Autowired
    public Translation() {}

    @Autowired
    @Builder
    public Translation(String text, Transcript transcript, Country country)
    {
        this.text = text;
        this.transcript = transcript;
        this.country = country;
    }
}
