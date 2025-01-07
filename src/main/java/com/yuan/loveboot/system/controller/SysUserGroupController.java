package com.yuan.loveboot.system.controller;

import com.yuan.loveboot.common.utils.Result;
import com.yuan.loveboot.system.service.SysUserGroupService;
import com.yuan.loveboot.system.vo.SysUserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户分组
 *
 * @author Maverick
 */
@RestController
@RequestMapping("sys/userGroup")
@RequiredArgsConstructor
@Tag(name = "用户分组管理")
public class SysUserGroupController {
    private final SysUserGroupService sysUserGroupService;


    @GetMapping()
    @Operation(summary = "查询分组中的另一用户资料")
    public Result<SysUserProfileVO> getOtherUserProfile() {
        SysUserProfileVO vo = sysUserGroupService.fetchOtherUserProfile();

        return Result.ok(vo);
    }

    @PostMapping()
    @Operation(summary = "保存用户分组")
    public Result<Boolean> save(int otherUserId) {
        sysUserGroupService.save(otherUserId);

        return Result.ok();
    }


    @DeleteMapping()
    @Operation(summary = "删除用户分组")
    public Result<Boolean> delete() {
        sysUserGroupService.delete();

        return Result.ok();
    }


}
