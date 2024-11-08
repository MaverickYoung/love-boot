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
public class SysUserBaseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5139478644603483246L;


    @Schema(description = "用户名")
    @OptionalSize(min = 2, max = 50, message = "用户名不合规")
    private String username;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "昵称")
    @OptionalSize(min = 1, max = 50, message = "昵称不合规")
    private String nickname;

}
