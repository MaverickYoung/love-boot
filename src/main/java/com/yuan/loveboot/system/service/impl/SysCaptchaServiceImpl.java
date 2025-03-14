package com.yuan.loveboot.system.service.impl;

import com.yuan.loveboot.common.utils.ArithmeticCaptcha;
import com.yuan.loveboot.common.utils.TokenUtil;
import com.yuan.loveboot.system.service.SysCaptchaService;
import com.yuan.loveboot.system.vo.SysCaptchaVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 验证码
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysCaptchaServiceImpl implements SysCaptchaService {

    private final SysCacheServiceImpl sysCacheService;

    @Value("${love.enableCaptcha}")
    private boolean captchaEnable;

    @Override
    public SysCaptchaVO generate() {
        // 生成验证码key
        String key = TokenUtil.generate();

        // 生成算术类型验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(150, 50);
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        String value = captcha.text();  // 获取运算的结果：5
        String image = captcha.toBase64();

        // 保存到缓存
        sysCacheService.cacheCaptcha(key, value);

        // 封装返回数据
        SysCaptchaVO captchaVO = new SysCaptchaVO();
        captchaVO.setKey(key);
        captchaVO.setImage(image);

        return captchaVO;

    }

    @Override
    public boolean isCaptchaEnabled() {
        return captchaEnable;
    }

    @Override
    public boolean validate(String key, String code) {
        // 如果关闭了验证码，则直接效验通过
        if (!isCaptchaEnabled()) {
            return true;
        }

        if (StringUtils.isBlank(key) || StringUtils.isBlank(code)) {
            return false;
        }

        // 获取验证码
        String captcha = sysCacheService.getCaptcha(key);
        sysCacheService.deleteCaptcha(key);

        // 效验成功
        return code.equalsIgnoreCase(captcha);
    }

}
