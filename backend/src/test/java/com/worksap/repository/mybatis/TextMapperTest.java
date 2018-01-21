package com.worksap.repository.mybatis;

import com.worksap.Application;
import com.worksap.model.dto.Pager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@Slf4j
public class TextMapperTest {

    @Resource
    TextMapper textMapper;
    @Test
    public void findAll() throws Exception {
        List list=textMapper.findAll("a",new Pager(1,5));
        Assert.assertTrue(list.size()>0);
        log.info(list.toString());

    }

}