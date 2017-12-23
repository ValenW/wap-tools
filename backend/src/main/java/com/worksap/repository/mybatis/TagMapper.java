package com.worksap.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TagMapper {
    void deleteTagToLink(@Param("tagId") int tagId);
}
