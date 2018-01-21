package com.worksap.repository.mybatis;

import com.worksap.model.dto.Pager;
import com.worksap.model.entity.TextResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TextMapper {

    @Select("select count(*) from text_resource where id like concat('%',#{keyword},'%') " +
            "or en like concat('%',#{keyword},'%') " +
            "or ja like concat('%',#{keyword},'%') ")
    long count(String keyword);

    @Select("select * from text_resource where id like concat('%',#{keyword},'%') " +
            "or en like concat('%',#{keyword},'%') " +
            "or ja like concat('%',#{keyword},'%') " +
            "limit #{pager.start},#{pager.limit}")
    List<TextResource> findAll(@Param("keyword") String keyword,
                               @Param("pager") Pager pager);
}
