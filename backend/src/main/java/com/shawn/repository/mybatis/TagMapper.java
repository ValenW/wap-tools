package com.shawn.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TagMapper {
    public void deleteTagToLink(@Param("tagId") int tagId);
}
