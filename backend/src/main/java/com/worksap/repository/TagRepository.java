package com.worksap.repository;

import com.worksap.model.entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
}
