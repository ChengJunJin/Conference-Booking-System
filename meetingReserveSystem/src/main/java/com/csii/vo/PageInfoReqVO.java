package com.csii.vo;

/**
 * 页信息
 * @createDate 2023-07-23
 */
public class PageInfoReqVO {
    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 页容量
     */
    private Integer pageSize;


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
