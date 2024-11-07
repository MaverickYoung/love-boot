package com.yuan.loveboot.system.service;


import com.yuan.loveboot.system.vo.SysCaptchaVO;

/**
 * 验证码
 *
 * @author wuyaun
 */
public interface SysCaptchaService {
    /**
     * 生成验证码
     */
    SysCaptchaVO generate();

    /**
     * 是否开启登录验证码
     *
     * @return true：开启  false：关闭
     */
    boolean isCaptchaEnabled();

    /**
     * 验证码效验
     *
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    boolean validate(String key, String code);
}
