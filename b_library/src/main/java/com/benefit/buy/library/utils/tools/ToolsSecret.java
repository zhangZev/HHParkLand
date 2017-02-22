package com.benefit.buy.library.utils.tools;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.benefit.buy.library.utils.Base64;

/**
 * 3DES加密工具类
 * @date 2012-10-11
 */
public class ToolsSecret {

    /**
     * // 密钥 private final static String secretKey = "Security@xlh.cn#Developer_2016#"; // 向量 private final static
     * String iv = "0gzxly01";
     */
    // 密钥
    private final static String secretKey = "Security@xlh.cn#Developer_2016#";

    // 向量
    private final static String iv = "0gzxly01";

    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(enOther(plainText).getBytes(encoding));
        return Base64.encode(encryptData);
    }

    /**
     * 3DES解密
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decode(deOther(encryptText)));
        return new String(decryptData, encoding);
    }

    public static void main(String[] args) throws Exception {
        String code = ToolsSecret.encode(
                "login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456&login=test&pwd=123456");
        String then = ToolsSecret.decode("d2E+tPiQd1g=");
        System.out.println(code);
        System.out.println(then);
    }

    public static String enOther(String encode) {
        return encode.replaceAll("[+]", "-XX-").replaceAll(" ", "-X-");
    }

    public static String deOther(String decode) {
        return decode.replaceAll("-XX-", "+").replaceAll("-X-", " ");
    }
}
