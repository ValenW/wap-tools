package com.shawn.repository.mybatis;

import com.shawn.model.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LinkMapper {

    public Integer selecCount();

    public List<Link> selectAll();

    public List<Link> selectByTagId(@Param("tagId") int tagId);

    public void addTags(@Param("tagIds") List<Integer> tagIds,@Param("linkId") int linkId);

    void deleteTagByLink(@Param("linkId")int linkId);
}
