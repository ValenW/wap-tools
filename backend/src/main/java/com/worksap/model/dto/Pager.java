package com.worksap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager {

    /**
     * 当前页编号
     */
    private Integer curPage;
    /**
     * 每页记录数
     */
    private Integer pageSize;

    public long getStart(){
        if(curPage<1){
            throw new IllegalArgumentException(String.format("错误的当前页数%s",curPage));
        }
        return pageSize*(curPage-1);
    }

    public long getLimit(){
        return pageSize;
    }
}
