package com.shawn.web.controller;

import com.shawn.model.entity.Link;
import com.shawn.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        link = linkService.add(link);

        return link;
    }


}
