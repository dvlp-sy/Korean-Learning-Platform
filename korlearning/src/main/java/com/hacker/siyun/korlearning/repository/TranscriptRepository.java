package com.hacker.siyun.korlearning.repository;

import com.hacker.siyun.korlearning.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TranscriptRepository extends JpaRepository<Transcript, Long>
{
    public List<Transcript> findAllByVideo_VideoId(Long videoId);
}
