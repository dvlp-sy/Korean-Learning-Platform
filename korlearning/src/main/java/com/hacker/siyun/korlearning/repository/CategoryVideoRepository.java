package com.hacker.siyun.korlearning.repository;

import com.hacker.siyun.korlearning.model.CategoryVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CategoryVideoRepository extends JpaRepository<CategoryVideo, Long>
{
    public List<CategoryVideo> findAllByCategory_CategoryId(Long categoryId);
}
