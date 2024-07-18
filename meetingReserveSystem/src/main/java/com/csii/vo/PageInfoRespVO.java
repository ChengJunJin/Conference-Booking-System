package com.csii.vo;

/**
 * 返回页信息
 * @createDate 2023-07-23
 */
public class PageInfoRespVO {
    /**
     * 数据总条数
     */
    private Integer count;
    /**
     * 总页数
     */
    private Integer pageCount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
