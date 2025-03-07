package com.yuan.loveboot.system.controller;

import com.yuan.loveboot.common.utils.Result;
import com.yuan.loveboot.system.dto.SysAccountLoginDTO;
import com.yuan.loveboot.system.dto.SysTokenDTO;
import com.yuan.loveboot.system.service.SysAuthService;
import com.yuan.loveboot.system.service.SysCaptchaService;
import com.yuan.loveboot.system.service.SysUserTokenService;
import com.yuan.loveboot.system.vo.SysCaptchaVO;
import com.yuan.loveboot.system.vo.SysUserTokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证管理
 *
 * @author Maverick
 */
@RestController
@RequestMapping("sys/auth")
@Tag(name = "认证管理")
@RequiredArgsConstructor
public class SysAuthController {
    private final SysCaptchaService sysCaptchaService;
    private final SysAuthService sysAuthService;
    private final SysUserTokenService sysUserTokenService;

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    public Result<SysCaptchaVO> captcha() {
        SysCaptchaVO captchaVO = sysCaptchaService.generate();

        return Result.ok(captchaVO);
    }

    @GetMapping("captcha/enabled")
    @Operation(summary = "是否开启验证码")
    public Result<Boolean> captchaEnabled() {
        boolean enabled = sysCaptchaService.isCaptchaEnabled();

        return Result.ok(enabled);
    }

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public Result<SysUserTokenVO> login(@RequestBody @Valid SysAccountLoginDTO login) {
        SysUserTokenVO token = sysAuthService.loginByAccount(login);

        return Result.ok(token);
    }

    @PostMapping("token")
    @Operation(summary = "获取 访问令牌")
    public Result<SysUserTokenVO> token(@RequestBody SysTokenDTO dto) {
        SysUserTokenVO token = sysUserTokenService.refreshToken(dto.getRefreshToken());

        return Result.ok(token);
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout() {
        sysAuthService.logout();

        return Result.ok();
    }
}
