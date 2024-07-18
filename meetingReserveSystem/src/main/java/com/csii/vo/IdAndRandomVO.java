package com.csii.vo;

/**
 * 申请取消请求
 * @author yhx
 * @version 1.0
 * createTime 2023/8/10
 */

public class IdAndRandomVO {
    /**
     * 会议室预约ID
     */
    private Integer recordId;
    /**
     * 随机验证码
     */
    private String randomString;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }
}
