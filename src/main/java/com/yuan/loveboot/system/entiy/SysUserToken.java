package com.yuan.loveboot.system.entiy;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户令牌
 *
 * @author Maverick
 */
@Data
public class SysUserToken {
    @TableId
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 访问令牌过期时间
     */
    private LocalDateTime accessTokenExpire;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 刷新令牌过期时间
     */
    private LocalDateTime refreshTokenExpire;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
