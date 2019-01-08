package com.trotyzyq.common.util;

import java.util.ArrayList;
import java.util.List;

/** 分页工具 **/
public class PageUtil<T> {
    /** 默认页面**/
    private Integer DEFAULT_PAGENUMBER = 1;

    /** 默认每页的数量**/
    private Integer DEFAULT_PAGESIZE = 10;

    /** 当前页面 **/
    private Integer pageNumber = DEFAULT_PAGENUMBER;

    /** 每页的数量**/
    private Integer pageSize = DEFAULT_PAGESIZE;

    /** 一共有多少页**/
    private Integer pageCount = 0;

    /** 截取的list**/
    private List<T> pageList = new ArrayList<>();

    /**
     * 开始分页传入参数
     * @param pageNumber
     * @param pageSize
     */
    public void startPage(Integer pageNumber, Integer pageSize){
        if(pageNumber == null){
            return;
        }
        if(pageNumber < 0){
            throw new RuntimeException("pageNumber is wrong");
        }
        this.pageNumber =  pageNumber;

        if(pageSize == null){
            return;
        }
        if(pageSize < 0){
            throw new RuntimeException("pageSize is wrong");
        }
        this.pageSize = pageSize;
    }

    /**
     * 获取mysql limit 开始的位置
     */
    public Integer getStartLimit(){
        return pageSize * (pageNumber - 1);
    }

    /**
     * 获取mysql limit 结束的位置
     * @return
     */
    public Integer getEndLimit(){
        return (pageNumber) * pageSize;
    }

    /**
     * 获得一页数量
     */
    public Integer getPageSize(){
        return pageSize;
    }

    /** 获取当前页**/
    public Integer getCurrentPage(){
        return pageNumber;
    }

    /**
     * 通过startPage的参数，截取list
     * @param beSubList 需要被截取的list
     * @return 截取后的list
     */
    public List<T> getList(List<T> beSubList){
        int start = getStartLimit();
        int end = getEndLimit();
        /** 如果小于list最小长度**/
        if(start > beSubList.size()){
            start = 0;
        }
        /** 如果大于list最大长度**/
        if(end >= beSubList.size()){
            end = beSubList.size();
        }
        this.pageList = beSubList.subList(start,end);
        int beSubListSize = beSubList.size();
        /** 三元表达式计算pageCount**/
        int pageCount = (beSubListSize % this.pageSize) > 0 ? (beSubListSize / this.pageSize) + 1 : (beSubListSize / this.pageSize);
        if (pageCount == 0){
            pageCount = 1;
        }
        this.pageCount =  pageCount;
        return this.pageList;
    }

    /**
     * 命中了多少条数
     */
    public int getTotal(){
        return pageList.size();
    }

    /**
     * 一共有多少页
     */
    public int getPageCount(){
        return pageCount;
    }
}
