package com.yuan.loveboot.system.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yuan.loveboot.mybatis.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 *
 * @author Maverick
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BasePO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @TableField(select = false)
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像，base64
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

}
