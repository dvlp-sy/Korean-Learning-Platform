package com.hacker.siyun.korlearning.repository;

import com.hacker.siyun.korlearning.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CountryRepository extends JpaRepository<Country, Long>
{

}
