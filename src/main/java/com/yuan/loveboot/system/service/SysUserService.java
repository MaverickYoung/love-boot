package com.yuan.loveboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.loveboot.system.entiy.SysUser;
import com.yuan.loveboot.system.vo.SysUserAvatarVO;
import com.yuan.loveboot.system.vo.SysUserBaseVO;
import com.yuan.loveboot.system.vo.SysUserPasswordVO;
import com.yuan.loveboot.system.vo.SysUserVO;

/**
 * 系统用户
 *
 * @author Maverick
 */
public interface SysUserService extends IService<SysUser> {
    void save(SysUserVO vo);

    void update(SysUserBaseVO vo);

    void updateAvatar(SysUserAvatarVO avatar);

    /**
     * 更新密码
     *
     * @param vo 修改信息
     */
    void updatePassword(SysUserPasswordVO vo);

    /**
     * 获取当前用户的信息
     *
     * @return 用户信息
     */
    SysUserBaseVO getUser();
}
