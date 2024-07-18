package com.csii.vo;

import java.util.List;

/**
 * 返回列表信息
 * @createDate 2023-07-23
 */
public class ListInfoVO extends PageInfoRespVO {
    private List<Object> dataList;

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }
}
