package com.yuan.loveboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.crypto.AesPasswordEncoder;
import com.yuan.loveboot.exception.ServerException;
import com.yuan.loveboot.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.system.convert.SysUserConvert;
import com.yuan.loveboot.system.dao.SysUserDao;
import com.yuan.loveboot.system.entiy.SysUser;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.system.service.SysUserService;
import com.yuan.loveboot.system.vo.SysUserAvatarVO;
import com.yuan.loveboot.system.vo.SysUserBaseVO;
import com.yuan.loveboot.system.vo.SysUserPasswordVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {
    private final SysCacheService sysCacheService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysUserVO vo) {
        SysUser entity = SysUserConvert.INSTANCE.convert(vo);

        // 判断用户名是否存在
        SysUser user = baseMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }

        String password = StringUtils.isBlank(entity.getPassword()) ? null : AesPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(password);

        // 保存用户
        baseMapper.insert(entity);
    }

    @Override
    public void update(SysUserBaseVO vo) {
        SysUser entity = SysUserConvert.INSTANCE.convert(vo);

        // 判断用户名是否存在
        SysUser user = baseMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }

        // 更新用户
        entity.setId(sysCacheService.getUserId());
        updateById(entity);
    }


    @Override
    public void updateAvatar(SysUserAvatarVO avatar) {
        SysUser entity = new SysUser();
        entity.setId(sysCacheService.getUserId());
        entity.setAvatar(avatar.getAvatar());
        updateById(entity);
    }

    @Override
    public void updatePassword(SysUserPasswordVO vo) {
        String password = AesPasswordEncoder.encode(vo.getPassword());
        String newPassword = AesPasswordEncoder.encode(vo.getNewPassword());
        if (password.equals(newPassword)) {
            throw new ServerException("新旧密码不能相同");
        }
        Integer userId = sysCacheService.getUserId();
        LambdaQueryWrapper<SysUser> query = Wrappers.lambdaQuery();
        query.eq(SysUser::getId, userId);
        query.eq(SysUser::getPassword, password);
        SysUser user = getOne(query);

        if (user == null) {
            throw new ServerException("旧密码错误");
        }
        SysUser user1 = new SysUser();
        user1.setId(userId);
        user1.setPassword(newPassword);
        updateById(user1);
    }

    @Override
    public SysUserBaseVO getUser() {
        SysUser user = getById(sysCacheService.getUserId());
        return SysUserConvert.INSTANCE.convert(user);
    }
}
