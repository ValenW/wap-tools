package com.shawn.service;

import com.shawn.model.entity.Link;
import com.shawn.repository.LinkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkService {

    @Resource
    private LinkRepository linkRepository;

    public List<Link> list() {
        return (List<Link>) linkRepository.findAll();
    }

    public Link add(Link link) {
        link.setCreateTime(LocalDateTime.now());
        return linkRepository.save(link);
    }

    public Link add(String name, String href) {
        Link link = new Link(name, href);
        return linkRepository.save(link);
    }

    public void delete(Integer id) {
        linkRepository.delete(id);
    }

    public void update(Link link) {
        if (link.getId() != null) {
            link.setUpdateTime(LocalDateTime.now());
            linkRepository.save(link);
        }
    }


}
