package com.yuan.loveboot.system.service.impl;

import com.wf.captcha.SpecCaptcha;
import com.yuan.loveboot.common.utils.TokenUtil;
import com.yuan.loveboot.system.service.SysCaptchaService;
import com.yuan.loveboot.system.vo.SysCaptchaVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public SysCaptchaVO generate() {
        // 生成验证码key
        String key = TokenUtil.generate();

        // 生成验证码
        // 定义图形验证码的长、宽、验证码字符数
        SpecCaptcha specCaptcha = new SpecCaptcha(150, 50, 5);
        String value = specCaptcha.text().toLowerCase();
        String image =specCaptcha.toBase64();

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
        return false;
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
