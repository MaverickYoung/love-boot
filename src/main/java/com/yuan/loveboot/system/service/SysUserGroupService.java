package com.yuan.loveboot.system.service;

import com.yuan.loveboot.common.mybatis.service.BaseService;
import com.yuan.loveboot.system.po.SysUserGroup;
import com.yuan.loveboot.system.vo.SysUserProfileVO;

/**
 * 用户分组
 *
 * @author Maverick
 */
public interface SysUserGroupService extends BaseService<SysUserGroup> {
    /**
     * 保存用户分组
     *
     * @param otherUserId 另一用户 ID
     */
    void save(int otherUserId);

    /**
     * 删除当前用户分组
     */
    void delete();

    /**
     * 查询分组中的另一用户 ID
     *
     * @return {@code null} 表示没有
     */
    Integer selectOtherUserId();

    /**
     * 查询分组中的另一用户资料
     *
     * @return {@code null} 表示没有
     */
    SysUserProfileVO fetchOtherUserProfile();
}
