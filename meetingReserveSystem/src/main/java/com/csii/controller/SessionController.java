package com.csii.controller;

import com.csii.service.ManagerInfoService;
import com.csii.service.impl.ManagerInfoServiceImpl;
import com.csii.util.MD5Util;
import com.csii.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

/**
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

@CrossOrigin
@RestController
public class SessionController {

    @Autowired
    private ManagerInfoService managerInfoService = new ManagerInfoServiceImpl();

    /**
     * @param request 请求
     * @param response 相应
     * @return 登录成功/失败
     * @throws UnsupportedEncodingException 字符设置异常
     */
    @PostMapping("login")
    public String login( HttpServletRequest request,
                          HttpServletResponse response ) throws UnsupportedEncodingException {
        //注意：生成session的时候需要生成一个用于验证密码的随机字符串

        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String plainText = request.getParameter("password");

        //数据库中存储的密码
        String originText = managerInfoService.selectPasswordFromName(username);
        //随机数
        String randomText = MD5Util.getRandomString();
        //处理后的用户输入的密码
        String convertText = MD5Util.md5(plainText,randomText);

        //验证成功返回token
        if (MD5Util.isRightPassword(originText,MD5Util.md5(convertText),randomText)){
            return TokenUtil.token(username,plainText);
        }
        return null;
    }
}
