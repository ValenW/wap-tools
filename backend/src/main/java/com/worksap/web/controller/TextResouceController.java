package com.worksap.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worksap.model.dto.Pager;
import com.worksap.model.dto.PaginatedResult;
import com.worksap.model.entity.TextResource;
import com.worksap.service.TextResourceService;
import com.worksap.util.NetworkUtil;

@RestController
@RequestMapping("/api")
@Slf4j
public class TextResouceController {
    @Resource
    private TextResourceService textResourceService;


    @GetMapping("/texts")
    public PaginatedResult getTextResources(String keyword, Pager pager,HttpServletRequest request) {
        log.info(NetworkUtil.getClientIpAddress(request));
        keyword=keyword!=null?keyword.trim().toLowerCase():"";
        return new PaginatedResult().setSize(textResourceService.count(keyword))
        .setData(textResourceService.findAll(keyword,pager));
    }

    @DeleteMapping("/text")
    public void delete( String id) {
        textResourceService.delete(id);
    }

    @PostMapping("/text/bulkDel")
    public void deleteBulk(@RequestBody List<String> ids){
        ids.forEach(textResourceService::delete);
    }

    @PostMapping("/text")
    public boolean add(@RequestBody TextResource textResource) {
        textResourceService.add(textResource);
        return true;
    }

    @PostMapping("/text/list")
    public boolean add(@RequestBody List<TextResource> list,HttpServletRequest request){
        String ip=NetworkUtil.getClientIpAddress(request);
        textResourceService.add(list,ip);
        return true;
    }
}
