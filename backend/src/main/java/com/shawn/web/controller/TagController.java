package com.shawn.web.controller;

import com.shawn.model.entity.Tag;
import com.shawn.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/tags")
    public Iterable<Tag> tags(){
        return tagService.all();
    }

    @GetMapping("/tag")
    public void add(String name){
        tagService.add(name,"");
    }

    @PostMapping("/tag")
    public void add(@RequestBody Tag tag){
        tagService.add(tag);
    }

    @DeleteMapping("/tag/{id}")
    public void delete(@PathVariable("id") Integer id){
        tagService.delete(id);
    }


}
