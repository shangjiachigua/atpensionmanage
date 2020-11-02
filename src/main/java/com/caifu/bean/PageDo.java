package com.caifu.bean;

import com.caifu.util.Utils;
import lombok.Data;

import java.util.List;

/**
 * ClassName :PageDo
 *
 * @author :  yb
 * @description ：
 * @date : 2020-08-26 16:47
 */
@Data
public class PageDo<T> {

    private Integer currentPage;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;
    private Integer pages;


    public PageDo(Integer currentPage, Integer pageSize,Integer pages, Integer totalCount, List<T> data) {
        this.currentPage = Utils.isNull(currentPage) ? 1 : currentPage;
        this.pageSize = Utils.isNull(pageSize) ? 10 : pageSize;
        this.totalCount = Utils.isNull(totalCount) ? 0 : totalCount;
        this.pages = Utils.isNull(pages) ? 0 : pages;
        this.data = data;
    }

    public PageDo(Long currentPage, Long pageSize, Integer totalCount, List<T> data) {
        this.currentPage = Utils.isNull(Integer.parseInt(currentPage+"")) ? 1 : Integer.parseInt(currentPage+"");
        this.pageSize = Utils.isNull(Integer.parseInt(pageSize+"")) ? 10 : Integer.parseInt(pageSize+"");
        this.totalCount = Utils.isNull(totalCount) ? 0 : totalCount;
        this.data = data;
    }
}
