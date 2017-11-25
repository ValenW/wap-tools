package com.shawn.service;

import com.shawn.model.entity.Tag;
import com.shawn.repository.TagRepository;
import org.hibernate.validator.constraints.SafeHtml;

public class TagService {
    private TagRepository tagRepository;

    public Iterable<Tag> all(){
        return tagRepository.findAll();
    }

    public void add(String name,String color){
        Tag tag=new Tag(name,color);
        tagRepository.save(tag);
    }
}
