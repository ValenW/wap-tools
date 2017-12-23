package com.worksap.repository.mybatis;

import com.worksap.Application;
import com.worksap.model.entity.Link;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
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
        List<Link> list = oc.selectAll();
        log.info(list.toString());
    }

    @Test
    public void addTags() {
        linkMapper.addTags(Arrays.asList(1, 2), 3);
    }

    @Test
    public void update() {
        List<Link> list = linkMapper.selectAll();
        Link link = list.get(0);
        log.info(link.toString());
        link.setHref("test test");
        link.setUpdateTime(LocalDateTime.now());
        linkMapper.update(link);

        log.info(linkMapper.selectAll().get(0).toString());
    }

}