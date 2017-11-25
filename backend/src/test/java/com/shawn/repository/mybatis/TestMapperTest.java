package com.shawn.repository.mybatis;

import com.shawn.Application;
import com.shawn.model.entity.Link;
import com.shawn.repository.LinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@Slf4j
public class TestMapperTest {

    @Autowired
    private LinkRepository mapper;

    @org.junit.Test
    public void test(){
        Link t=new Link();
        t.setName("test");
        t.setHref("sdfsdfds");
        mapper.save(t);
        System.out.println(mapper.findAll());
//        log.info(mapper.selecCount()+"");
    }

}