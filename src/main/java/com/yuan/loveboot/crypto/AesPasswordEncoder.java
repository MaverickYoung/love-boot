package com.yuan.loveboot.crypto;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密算法工具类
 *
 * @author Maverick
 */
public class AesPasswordEncoder {


    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 加密模式和填充方式
     */
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 密钥
     */
    private static final String SECRET_KEY = "roUs6v92pVsc7K";

    /**
     * 对原始密码进行加密
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码（Base64 编码）
     */
    public static String encode(CharSequence rawPassword) {
        try {
            // 初始化 Cipher 对象，用于加密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            // 将密码加密并编码为 Base64 字符串
            byte[] encryptedBytes = cipher.doFinal(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("加密错误", e);
        }
    }

    /**
     * 获取 SecretKeySpec 对象，用于初始化 Cipher
     *
     * @return SecretKeySpec 对象
     * @throws Exception 获取密钥时可能发生的异常
     */
    private static SecretKeySpec getSecretKey() throws Exception {
        // 生成 AES 密钥
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(SECRET_KEY.getBytes());
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
    }

    /**
     * 解密加密后的密码
     *
     * @param encryptedPassword 加密后的密码（Base64 编码）
     * @return 解密后的原始密码
     */
    public String decrypt(String encryptedPassword) {
        try {
            // 初始化 Cipher 对象，用于解密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            // 将 Base64 编码的密码解码并解密
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("解密错误", e);
        }
    }
}
