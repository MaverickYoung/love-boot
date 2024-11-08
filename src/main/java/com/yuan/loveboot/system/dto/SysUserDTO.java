package com.yuan.loveboot.system.dto;

import com.yuan.loveboot.validation.OptionalSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户
 *
 * @author Maverick
 */
@Data
@Schema(description = "用户")
public class SysUserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7126549982180054544L;

    @Schema(description = "用户名")
    @OptionalSize(min = 2, max = 50, message = "用户名不合规")
    private String username;

    @Schema(description = "密码")
    @OptionalSize(min = 4, max = 20, message = "密码不合规")
    private String password;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private int gender;

    @Schema(description = "昵称")
    @OptionalSize(min = 1, max = 50, message = "昵称不合规")
    private String nickname;

}
