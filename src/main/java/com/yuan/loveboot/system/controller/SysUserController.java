package com.yuan.loveboot.system.controller;

import com.yuan.loveboot.system.service.SysUserService;
import com.yuan.loveboot.system.vo.SysUserAvatarVO;
import com.yuan.loveboot.system.vo.SysUserBaseVO;
import com.yuan.loveboot.system.vo.SysUserPasswordVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import com.yuan.loveboot.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 用户管理
 *
 * @author Maverick
 */
@RestController
@RequestMapping("sys/user")
@AllArgsConstructor
@Tag(name = "用户管理")
public class SysUserController {
    private final SysUserService sysUserService;


    @GetMapping("info")
    @Operation(summary = "获取用户信息")
    public Result<SysUserBaseVO> info() {
        SysUserBaseVO user = sysUserService.getUser();

        return Result.ok(user);
    }

    @PutMapping("info")
    @Operation(summary = "修改登录用户信息")
    public Result<String> loginInfo(@RequestBody @Valid SysUserBaseVO vo) {
        sysUserService.update(vo);

        return Result.ok();
    }

    @PutMapping("avatar")
    @Operation(summary = "修改登录用户头像")
    public Result<String> avatar(@RequestBody SysUserAvatarVO avatar) {
        sysUserService.updateAvatar(avatar);

        return Result.ok();
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    public Result<String> password(@RequestBody @Valid SysUserPasswordVO vo) {
        sysUserService.updatePassword(vo);

        return Result.ok();
    }

    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public Result<String> save(@RequestBody @Valid SysUserVO vo) {
        sysUserService.save(vo);

        return Result.ok();
    }
}
