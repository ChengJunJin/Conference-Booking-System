package com.csii.entity;

/**
 * 管理员信息
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

public class ManagerInfo {
    /**
     * 管理员登录名
     */
    private String loginName;
    /**
     * 管理员登录密码
     */
    private String password;
    /**
     * 管理员邮箱
     */
    private String userEmail;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ManagerInfo() {
    }

    public ManagerInfo(String loginName, String password, String userEmail) {
        this.loginName = loginName;
        this.password = password;
        this.userEmail = userEmail;
    }

    public ManagerInfo(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }
}
