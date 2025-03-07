package com.yuan.loveboot.crypto;

import com.yuan.loveboot.common.crypto.AesPasswordEncoder;
import org.junit.jupiter.api.Test;

/**
 * 密码编码器测试
 *
 * @author Maverick
 */
class AesPasswordEncoderTest {
    private final AesPasswordEncoder encoder = new AesPasswordEncoder();

    @Test
    void decryptTest() {
        System.out.println(encoder.decrypt("uVeF7V0bd212I7n8fFZWLg=="));
    }
}