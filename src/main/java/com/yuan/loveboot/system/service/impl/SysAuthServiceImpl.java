package com.yuan.loveboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.common.crypto.AesPasswordEncoder;
import com.yuan.loveboot.common.exception.ServerException;
import com.yuan.loveboot.system.dto.SysAccountLoginDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.service.*;
import com.yuan.loveboot.system.vo.SysUserTokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
    private final SysCaptchaService sysCaptchaService;
    private final SysUserTokenService sysUserTokenService;
    private final SysUserService sysUserService;
    private final SysCacheService sysCacheService;


    @Override
    public SysUserTokenVO loginByAccount(SysAccountLoginDTO login) {
        // 验证码效验
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            throw new ServerException("验证码错误");
        }

        String password = AesPasswordEncoder.encode(login.getPassword());

        LambdaQueryWrapper<SysUser> query = Wrappers.lambdaQuery();
        query.eq(SysUser::getUsername, login.getUsername());
        query.eq(SysUser::getPassword, password);
        SysUser user = sysUserService.getOne(query);

        if (user == null) {
            throw new ServerException("用户名或密码错误");
        }

        // 生成 accessToken
        return sysUserTokenService.createToken(user.getId());
    }

    @Override
    public void logout() {
        // Token过期
        sysUserTokenService.expireToken(sysCacheService.getUserId());
    }

}
