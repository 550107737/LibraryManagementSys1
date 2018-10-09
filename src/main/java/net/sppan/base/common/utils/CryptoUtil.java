package net.sppan.base.common.utils;


import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.Random;

/**
 * 加密解密共通
 * MD5
 */
public class CryptoUtil {

    private static Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    /**
     * 将字符串以md5加密
     *
     * @param value
     *            被加密的字符串
     * @param key
     *            密码
     * @return 加密后的字符串
     */
    public static String md5(String value, String key) {
        return encoder.encodePassword(value, key);
    }

    /**
     * 将字符串以md5加密
     *
     * @param value 被加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String value) {
        return encoder.encodePassword(value, null);
    }

    public static String getSecretKey() {
        return md5(String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 生成验证码
     *
     * @param length
     * @return
     */
    public static String getVerifyCode(int length) {
        Random random = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--------------");
        System.out.println(md5("happysummerdeer"));
        System.out.println(md5("adfdafdfasdfafds"));

    }
}
