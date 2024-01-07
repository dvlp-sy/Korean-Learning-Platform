package com.hacker.siyun.korlearning.repository;

import com.hacker.siyun.korlearning.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VideoRepository extends JpaRepository<Video, Long>
{

}
