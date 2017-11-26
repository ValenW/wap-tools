package com.shawn.service;

import com.shawn.model.entity.Link;
import com.shawn.repository.LinkRepository;
import com.shawn.repository.mybatis.LinkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {

    @Resource
    private LinkRepository linkRepository;

    @Resource
    private LinkMapper linkMapper;


    public List<Link> list() {

        return linkMapper.selectAll();
    }

    public Link add(Link link) {
        List<Integer> tagIds = null;
        if (link.getTags() != null) {
            tagIds = link.getTags().stream()
                    .map(tag -> tag.getId())
                    .collect(Collectors.toList());
        }
        link.setCreateTime(LocalDateTime.now());
        Link newLink= linkRepository.save(link);
        linkMapper.addTags(tagIds,newLink.getId());
        return newLink;
    }

    public Link add(String name, String href) {
        Link link = new Link(name, href);
        return linkRepository.save(link);
    }

    public void delete(Integer id) {
        linkRepository.delete(id);
        linkMapper.deleteTagByLink(id);
    }

    public void update(Link link) {
        if (link.getId() != null) {
            link.setUpdateTime(LocalDateTime.now());
            linkRepository.save(link);
        }
    }


}
