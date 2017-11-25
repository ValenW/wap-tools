package com.shawn.repository;

import com.shawn.model.entity.Link;
import com.shawn.model.entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
}
