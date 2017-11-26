package com.shawn.service;

import com.shawn.model.entity.Tag;
import com.shawn.repository.TagRepository;
import com.shawn.repository.mybatis.TagMapper;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagService {
    @Resource
    private TagRepository tagRepository;

    @Resource
    private TagMapper tagMapper;

    public Iterable<Tag> all() {
        return tagRepository.findAll();
    }

    public void add(String name, String color) {
        Tag tag = new Tag(name, color);
        tagRepository.save(tag);
    }

    @Transactional
    public void delete(Integer id) {
        tagRepository.delete(id);
        tagMapper.deleteTagToLink(id);
    }

    @Transactional
    public void delete(List<Integer> ids) {
        ids.forEach(this::delete);
    }


    public void add(Tag tag) {
        tagRepository.save(tag);
    }
}
