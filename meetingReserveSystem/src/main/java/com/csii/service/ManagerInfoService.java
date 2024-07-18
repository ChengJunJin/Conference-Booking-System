package com.csii.service;

/**
 * 管理员信息的service接口
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

public interface ManagerInfoService {

    /**
     * 登录验证
     * @param loginName
     * @return password
     */
    String selectPasswordFromName(String loginName);
}
