package com.yuan.loveboot.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户昵称和头像
 *
 * @author Maverick
 */
@Data
@Schema(description = "用户信息")
public class SysUserProfileVO {
    @Schema(description = "用户ID")
    private int id;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像，base64")
    private String avatar;
}
