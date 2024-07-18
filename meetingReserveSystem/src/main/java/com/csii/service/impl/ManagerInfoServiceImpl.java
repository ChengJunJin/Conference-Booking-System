package com.csii.service.impl;

import com.csii.mapper.ManagerInfoMapper;
import com.csii.service.ManagerInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 管理员信息实现类
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

@Service
public class ManagerInfoServiceImpl implements ManagerInfoService {

    @Autowired
    ManagerInfoMapper managerInfoMapper;

    /**
     * 登录验证
     * @param loginName 用户名
     * @return password
     */
    @Override
    @Transactional
    public String selectPasswordFromName(String loginName) {
        return managerInfoMapper.selectPasswordFromName(loginName);
    }
}
