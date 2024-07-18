package com.csii.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * MD5加解密工具类
 * @createDate 2023-07-23
 */
public class MD5Util {
    private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);
    /**
     * 使用随机码与密码拼接生成密码进行加密
     * @param plainText 输入的密码
     * @param randomText 随机值
     * @return 加密之后的值
     */
    public static String md5(String plainText, String randomText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        md5code += randomText;
        return md5code;
    }

    /**
     * 使用md5的算法进行加密（数据库中存储的密码）
     * @param plainText 原始的字符串
     * @return 加密之后的字符串
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error("没有md5这个算法！");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }

    /**
     * 判断密码是否正确
     * @param originText 数据库中存储的加密之后的密码
     * @param convertText 用户输入的转换之后的密码
     * @param randomText 随机字符串
     * @return 密码是否正确
     */
    public static boolean isRightPassword(String originText, String convertText, String randomText) {
        originText += randomText;
        String password = md5(originText);
        return password.equals(convertText);
    }


    /**
     * 生成用于验证密码所用的随机字符串
     * @return 用于验证密码所用的随机字符串
     */
    public static String getRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
