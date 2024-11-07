package com.yuan.loveboot.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改密码
 *
 * @author Maverick
 */
@Data
@Schema(description = "用户修改密码")
public class SysUserPasswordVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4218800782281847645L;

    @Schema(description = "原密码")
    @NotBlank(message = "原密码不能为空")
    private String password;

    @Schema(description = "新密码，密码长度为 4-20 位")
    @Size(min = 4, max = 20, message = "新密码长度为 4-20 位")
    private String newPassword;

}