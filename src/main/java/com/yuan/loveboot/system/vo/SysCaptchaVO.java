package com.yuan.loveboot.system.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 图片验证码
 *
 * @author Maverick
 */
@Data
public class SysCaptchaVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2641654265773016926L;

    /**
     * key
     */
    private String key;

    /**
     * image base64
     */
    private String image;
}
