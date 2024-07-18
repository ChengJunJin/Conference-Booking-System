package com.csii.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员信息mapper接口
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

@Mapper
public interface ManagerInfoMapper {
    /**
     * 根据用户名获取用户密码
     * @param loginName 用户名
     * @return 用户密码
     */
    String selectPasswordFromName(@Param("loginName") String loginName);

}
