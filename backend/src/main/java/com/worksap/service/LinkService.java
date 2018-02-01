package com.worksap.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.worksap.model.entity.Link;
import com.worksap.repository.LinkRepository;
import com.worksap.repository.mybatis.LinkMapper;

@Service
public class LinkService {

    private final static String CACHE_LINK="links";

    @Resource
    private LinkRepository linkRepository;

    @Resource
    private LinkMapper linkMapper;


    @Cacheable(CACHE_LINK)
    public List<Link> list() {

        return linkMapper.selectAll();
    }

    @CacheEvict(value = CACHE_LINK,allEntries = true)
    public Link addOrUpdate(Link link) {
        List<Integer> tagIds = null;
        if (link.getTags() != null) {
            tagIds = link.getTags().stream()
                    .map(tag -> tag.getId())
                    .collect(Collectors.toList());
        }

        if (link.getId() == null) {
            link.setCreateTime(LocalDateTime.now());
            Link newLink = linkRepository.save(link);
            if(!tagIds.isEmpty()) {
                linkMapper.addTags(tagIds, newLink.getId());
            }
            return newLink;
        } else {
            link.setUpdateTime(LocalDateTime.now());
            linkMapper.deleteTagByLink(link.getId());
            if (!tagIds.isEmpty()) {
                linkMapper.addTags(tagIds, link.getId());
            }
            linkMapper.update(link);
            return link;
        }
    }

    @CacheEvict(value = CACHE_LINK,allEntries = true)
    public Link add(String name, String href) {
        Link link = new Link(name, href);
        return linkRepository.save(link);
    }

    @CacheEvict(value = CACHE_LINK,allEntries = true)
    public void delete(Integer id) {
        linkRepository.delete(id);
        linkMapper.deleteTagByLink(id);
    }

    @CacheEvict(value = CACHE_LINK,allEntries = true)
    public void update(Link link) {
        if (link.getId() != null) {
            link.setUpdateTime(LocalDateTime.now());
            linkRepository.save(link);
        }
    }


}
