package com.yuan.loveboot.system.po;

import com.yuan.loveboot.common.mybatis.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分组
 *
 * @author Maverick
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserGroup extends BasePO {
    /**
     * 用户1 ID
     */
    private Integer user1Id;
    /**
     * 用户2 ID
     */
    private Integer user2Id;
}
