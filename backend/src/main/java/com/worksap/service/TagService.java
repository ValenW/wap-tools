package com.worksap.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.model.entity.Tag;
import com.worksap.repository.TagRepository;
import com.worksap.repository.mybatis.TagMapper;

@Service
public class TagService {

    private final static String CACHE_TAG="tags";
    @Resource
    private TagRepository tagRepository;

    @Resource
    private TagMapper tagMapper;

    @Cacheable(CACHE_TAG)
    public Iterable<Tag> all() {
        return tagRepository.findAll();
    }

    public void add(String name, String color) {
        Tag tag = new Tag(name, color);
        tagRepository.save(tag);
    }

    @CacheEvict(value = CACHE_TAG,allEntries = true)
    @Transactional
    public void delete(Integer id) {
        tagRepository.delete(id);
        tagMapper.deleteTagToLink(id);
    }

   @CacheEvict(value = CACHE_TAG,allEntries = true)
    @Transactional
    public void delete(List<Integer> ids) {
        ids.forEach(this::delete);
    }


   @CacheEvict(value = CACHE_TAG,allEntries = true)
    public void add(Tag tag) {
        tagRepository.save(tag);
    }
}
