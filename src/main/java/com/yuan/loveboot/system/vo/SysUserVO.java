package com.yuan.loveboot.system.vo;

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
public class SysUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5139478644603483246L;

    @Schema(description = "用户ID")
    private int id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像，base64")
    private String avatar;

    @Schema(description = "背景图，base64")
    private String background;

    @Schema(description = "性别")
    private int gender;

    @Schema(description = "昵称")
    private String nickname;

}
