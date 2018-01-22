package com.worksap.service;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.worksap.Application;
import com.worksap.model.dto.Pager;
import com.worksap.model.entity.TextResource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@Slf4j
@Ignore
public class TextResourceServiceTest {

    @Resource
    private TextResourceService textResourceService;

    @Test
    public void findAll() throws Exception {
        List<TextResource> list= textResourceService.findAll("",new Pager(1,10000));
        list.forEach(it->{
           it.setId(it.getId().replace("\"",""));
           it.setEn(it.getEn().replace("\"",""));
           it.setJa(it.getJa().replace("\"",""));
           textResourceService.add(it);
        });

    }

}