package com.hacker.siyun.korlearning.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table
@Entity
public class Video
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId;

    @Column
    private String link;

    @Column
    private String videoTitle;

    @Column
    private String creator;

    @Column
    private Long duration;

    @Column
    private Boolean isDefault;

    @Column
    private Long views;

    @Column
    @CreatedDate
    private Date createdAt;

    @Column
    private Long youtubeViews;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CategoryVideo> categoryVideos = new HashSet<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Transcript> transcripts = new HashSet<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserVideo> userVideos = new HashSet<>();

    @Autowired
    public Video() { }
    @Autowired
    @Builder
    public Video(String link, String videoTitle, String creator, Long duration, Boolean isDefault, Long views, Date createdAt, Long youtubeViews)
    {
        this.link = link;
        this.videoTitle = videoTitle;
        this.creator = creator;
        this.duration = duration;
        this.isDefault = isDefault;
        this.views = views;
        this.createdAt = createdAt;
        this.youtubeViews = youtubeViews;
    }


}
