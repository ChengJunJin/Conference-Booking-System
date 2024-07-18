package com.csii.util;

import java.util.Random;

/**
 * 生成随机验证码的工具类
 * @author yhx
 * @version 1.0
 * createTime 2023/8/10a
 */

public class RandomStringGeneratorUtil {

    public static String generateRandom() {
        return generateRandomString(5);
    }

    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
