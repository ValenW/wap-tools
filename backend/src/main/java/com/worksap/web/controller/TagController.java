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

import com.worksap.model.entity.Tag;
import com.worksap.service.TagService;

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

    @PostMapping("/tag/delete")
    public void delete(@RequestBody List<Integer> ids){
        tagService.delete(ids);
    }


}
