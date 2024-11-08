package com.yuan.loveboot.system.service;

import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.system.po.SysUserToken;
import com.yuan.loveboot.system.vo.SysUserTokenVO;

/**
 * 用户Token
 *
 * @author Maverick
 */
public interface SysUserTokenService extends BaseService<SysUserToken> {

    /**
     * 根据用户ID，生成用户Token
     *
     * @param userId 用户ID
     * @return 用户Token
     */
    SysUserTokenVO createToken(Integer userId);

    /**
     * 根据RefreshToken，生成新Token
     *
     * @param refreshToken 刷新令牌
     * @return 访问令牌
     */
    String refreshToken(String refreshToken);

    /**
     * Token过期
     *
     * @param userId 用户ID
     */
    void expireToken(Integer userId);

}
