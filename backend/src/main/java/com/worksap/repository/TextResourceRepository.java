package com.worksap.repository;

import org.springframework.data.repository.CrudRepository;

import com.worksap.model.entity.TextResource;

public interface TextResourceRepository extends CrudRepository<TextResource, String> {

}
