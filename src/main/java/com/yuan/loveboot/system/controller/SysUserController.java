package com.yuan.loveboot.system.controller;

import com.yuan.loveboot.system.dto.SysUserAvatarDTO;
import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.dto.SysUserPasswordDTO;
import com.yuan.loveboot.system.service.SysUserService;
import com.yuan.loveboot.system.vo.SysUserVO;
import com.yuan.loveboot.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 用户管理
 *
 * @author Maverick
 */
@RestController
@RequestMapping("sys/user")
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class SysUserController {
    private final SysUserService sysUserService;


    @GetMapping("info")
    @Operation(summary = "获取用户信息")
    public Result<SysUserVO> info() {
        SysUserVO user = sysUserService.getUser();

        return Result.ok(user);
    }

    @PutMapping("info")
    @Operation(summary = "修改登录用户信息")
    public Result<String> loginInfo(@RequestBody @Valid SysUserBaseDTO dto) {
        sysUserService.update(dto);

        return Result.ok();
    }

    @PutMapping("avatar")
    @Operation(summary = "修改登录用户头像")
    public Result<String> avatar(@RequestBody @Valid SysUserAvatarDTO avatar) {
        sysUserService.updateAvatar(avatar);

        return Result.ok();
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    public Result<String> password(@RequestBody @Valid SysUserPasswordDTO dto) {
        sysUserService.updatePassword(dto);

        return Result.ok();
    }

    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public Result<String> save(@RequestBody @Valid SysUserDTO dto) {
        sysUserService.save(dto);

        return Result.ok();
    }
}
