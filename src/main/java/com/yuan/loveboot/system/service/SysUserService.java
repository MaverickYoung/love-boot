package com.yuan.loveboot.system.service;

import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.system.dto.SysUserAvatarDTO;
import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.dto.SysUserPasswordDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.vo.SysUserVO;

/**
 * 系统用户
 *
 * @author Maverick
 */
public interface SysUserService extends BaseService<SysUser> {
    void save(SysUserDTO dto);

    void update(SysUserBaseDTO dto);

    void updateAvatar(SysUserAvatarDTO avatar);

    /**
     * 更新密码
     *
     * @param dto 修改信息
     */
    void updatePassword(SysUserPasswordDTO dto);

    /**
     * 获取当前用户的信息
     *
     * @return 用户信息
     */
    SysUserVO getUser();
}
