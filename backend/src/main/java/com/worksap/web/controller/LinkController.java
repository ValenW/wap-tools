package com.worksap.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worksap.model.entity.Link;
import com.worksap.service.LinkService;

@RestController
@RequestMapping("/api")
public class LinkController {

    @Resource
    private LinkService linkService;


    @GetMapping("/links")
    public List<Link> getLinks() {
        return linkService.list();
    }

    @DeleteMapping("/link/{id}")
    public void delete(@PathVariable("id") Integer id) {
        linkService.delete(id);
    }

    @PostMapping("/link")
    public Link add(@RequestBody Link link) {

        link = linkService.addOrUpdate(link);

        return link;
    }


}
