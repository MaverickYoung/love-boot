package com.yuan.loveboot.system.service;

import com.yuan.loveboot.system.query.SysAccountLoginQuery;
import com.yuan.loveboot.system.vo.SysUserTokenVO;

/**
 * 权限认证
 *
 * @author Maverick
 */
public interface SysAuthService {
    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    SysUserTokenVO loginByAccount(SysAccountLoginQuery login);


    /**
     * 退出登录
     */
    void logout();


}
