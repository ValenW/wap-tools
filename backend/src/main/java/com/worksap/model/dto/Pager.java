package com.worksap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import com.worksap.util.StringUtil;

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

    private String orderCol;

    private Integer order;

    public Pager(int curPage, int pageSize) {
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    public long getStart() {
        if (curPage < 1) {
            throw new IllegalArgumentException(String.format("错误的当前页数%s", curPage));
        }
        return pageSize * (curPage - 1);
    }

    public long getLimit() {
        return pageSize;
    }

    public String getOd() {
        return order!=null&&order.intValue() == -1 ? "DESC" : "ASC";
    }

    public String getOrderCol() {
        if (StringUtils.isBlank(orderCol)) {
            return null;
        }
        return StringUtil.camelToUnderline(orderCol);
    }




}
