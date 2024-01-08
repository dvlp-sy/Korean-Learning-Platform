package com.hacker.siyun.korlearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Table
@Entity
public class UserVideo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uservideo_id")
    private Long userVideoId;

    @Column
    @CreatedDate
    private Date createdAt;

    @Column
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    @JsonBackReference
    private Video video;

    @Builder
    public UserVideo(Date createdAt, Date updatedAt, User user, Video video)
    {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.video = video;
    }
}
