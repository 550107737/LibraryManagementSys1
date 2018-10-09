package net.sppan.base.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * DES加密解密共通
 *
 */
public class DesUtil {

    private final static String DES = "DES";
    private final static String DEFAULT_KEY = "s17ucAicatc0path";

    public static void main(String[] args) throws Exception {
        System.out.println("===================================");

        System.out.println(encrypt("2892297"));
        System.out.println(encrypt("张三"));
        System.out.println(encrypt("13612345678"));
        System.out.println(encrypt("370681199303140031"));
        System.out.println(encrypt("744376"));
        System.out.println(encrypt("6013821100014497327"));
        System.out.println(encrypt("6013821100014497328"));
        System.out.println(encrypt("18521564125"));
        System.out.println(encrypt("18576589843"));

        System.out.println("===================================");
        System.out.println(decrypt("ijtFEK6cuOU="));

        //String data = "\"status_code\":901,\"user_code\":\"10161434607670602510\",\"user_id\":QMQcBf7uCmw=,\"authentic_name\":\"++maaC9g+cmjZDZ1/3ENpA==\",\"is_VIP\":0,\"id_card\":\"+d1OVDBrickPYh+RlmZL2j+gz6nrxVxY\",\"trade_ever\":0";
        //JsonUtil.json2Entity(data, Map.class);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encrypt(String data) {
        return encrypt(data, DEFAULT_KEY);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decrypt(String data) {
        return decrypt(data, DEFAULT_KEY);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     */
    public static String encrypt(String data, String key) {
        try {
            byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
            return new BASE64Encoder().encode(bt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     */
    public static String decrypt(String data, String key) {
        try {
            if (data == null) {
                return null;
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];
        }

    }


    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];
        }
    }
}
