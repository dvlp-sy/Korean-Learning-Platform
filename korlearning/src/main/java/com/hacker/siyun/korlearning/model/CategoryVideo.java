package com.hacker.siyun.korlearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Table
@Entity
public class CategoryVideo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryvideo_id")
    private Long categoryVideoId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    @JsonBackReference
    private Video video;

    @Autowired
    public CategoryVideo() {}

    @Autowired
    @Builder
    public CategoryVideo(Category category, Video video)
    {
        this.category = category;
        this.video = video;
    }


}
