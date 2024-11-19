package com.yuan.loveboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.enums.ResponseCode;
import com.yuan.loveboot.exception.ServerException;
import com.yuan.loveboot.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.properties.SecurityProperties;
import com.yuan.loveboot.system.convert.SysUserTokenConvert;
import com.yuan.loveboot.system.dao.SysUserTokenDao;
import com.yuan.loveboot.system.po.SysUserToken;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.system.service.SysUserTokenService;
import com.yuan.loveboot.system.vo.SysUserTokenVO;
import com.yuan.loveboot.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户Token
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class SysUserTokenServiceImpl extends BaseServiceImpl<SysUserTokenDao, SysUserToken> implements SysUserTokenService {
    private final SysCacheService sysCacheService;

    @Override
    public SysUserTokenVO createToken(int userId) {
        // 生成token
        // UUID去除连字符
        String accessToken = TokenUtil.generate();
        String refreshToken = TokenUtil.generate();

        SysUserToken entity = new SysUserToken();
        entity.setUserId(userId);
        entity.setAccessToken(accessToken);
        entity.setRefreshToken(refreshToken);


        // 过期时间
        LocalDateTime now = LocalDateTime.now();
        entity.setAccessTokenExpire(now.plusSeconds(SecurityProperties.ACCESS_TOKEN_EXPIRE));
        entity.setRefreshTokenExpire(now.plusSeconds(SecurityProperties.REFRESH_TOKEN_EXPIRE));

        // 是否存在Token
        SysUserToken tokenEntity = baseMapper.selectOne(new LambdaQueryWrapper<SysUserToken>().eq(SysUserToken::getUserId, userId));
        if (tokenEntity == null) {
            baseMapper.insert(entity);
        } else {
            entity.setId(tokenEntity.getId());
            baseMapper.updateById(entity);
        }

        sysCacheService.cacheAccessToken(userId, accessToken);
        return SysUserTokenConvert.INSTANCE.convert(entity);
    }

    @Override
    public SysUserTokenVO refreshToken(String refreshToken) {
        LocalDateTime now = LocalDateTime.now();

        LambdaQueryWrapper<SysUserToken> query = Wrappers.lambdaQuery();
        query.eq(SysUserToken::getRefreshToken, refreshToken);
        query.ge(SysUserToken::getRefreshTokenExpire, now);

        // 不存在，则表示refreshToken错误，或者已过期
        SysUserToken entity = baseMapper.selectOne(query);
        if (entity == null) {
            throw new ServerException(ResponseCode.REFRESH_TOKEN_INVALID);
        }

        // 删除缓存信息
        sysCacheService.deleteAccessToken(entity.getUserId());

        // 生成新 accessToken
        SysUserTokenVO vo = new SysUserTokenVO();
        vo.setAccessToken(TokenUtil.generate());
        vo.setRefreshToken(TokenUtil.generate());

        entity.setAccessToken(vo.getAccessToken());
        entity.setAccessTokenExpire(now.plusSeconds(SecurityProperties.ACCESS_TOKEN_EXPIRE));
        entity.setRefreshToken(vo.getRefreshToken());
        entity.setRefreshTokenExpire(now.plusSeconds(SecurityProperties.REFRESH_TOKEN_EXPIRE));

        // 更新
        sysCacheService.cacheAccessToken(entity.getUserId(), vo.getAccessToken());
        baseMapper.updateById(entity);

        return vo;
    }

    @Override
    public void expireToken(int userId) {
        SysUserToken entity = new SysUserToken();

        sysCacheService.deleteAccessToken(entity.getUserId());

        LocalDateTime now = LocalDateTime.now();
        entity.setAccessTokenExpire(now);
        entity.setRefreshTokenExpire(now);

        baseMapper.update(entity, new LambdaQueryWrapper<SysUserToken>().eq(SysUserToken::getUserId, userId));
    }
}