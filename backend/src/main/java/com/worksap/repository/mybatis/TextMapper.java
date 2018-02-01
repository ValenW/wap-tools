package com.worksap.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.worksap.model.dto.Pager;
import com.worksap.model.entity.TextResource;

@Mapper
public interface TextMapper {


    Long count(String keyword);


    List<TextResource> findAll(@Param("keyword") String keyword,
                               @Param("pager") Pager pager);

}
