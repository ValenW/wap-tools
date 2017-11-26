package com.shawn.repository.mybatis;

import com.shawn.Application;
import com.shawn.model.entity.Link;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@Slf4j
public class LinkMapperTest {

    @Resource
    private LinkMapper linkMapper;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void selectAll() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        LinkMapper oc = sqlSession.getMapper(LinkMapper.class);
        List<Link> list=oc.selectAll();
        log.info(list.toString());
    }

    @Test
    public void addTags(){
        linkMapper.addTags(Arrays.asList(1,2),3);
    }

}