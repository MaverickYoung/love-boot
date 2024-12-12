package com.yuan.loveboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.common.crypto.AesPasswordEncoder;
import com.yuan.loveboot.common.exception.ServerException;
import com.yuan.loveboot.common.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.common.utils.FileUtil;
import com.yuan.loveboot.system.converter.SysUserConverter;
import com.yuan.loveboot.system.dao.SysUserDao;
import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.dto.SysUserPasswordDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.system.service.SysUserService;
import com.yuan.loveboot.system.vo.SysUserProfileVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户管理
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {
    private final SysCacheService sysCacheService;
    private final FileUtil fileUtil;

    // 从 application.yml 中读取默认头像路径
    @Value("${storage.path.defaultAvatar}")
    private String defaultAvatar;

    @Override
    public List<SysUserProfileVO> findByIds(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            throw new ServerException("用户ID数组不能为空");
        }
        List<SysUser> list = baseMapper.selectByIds(idList);

        return SysUserConverter.INSTANCE.convertProfile(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysUserDTO dto) {
        SysUser entity = SysUserConverter.INSTANCE.convert(dto);

        // 判断用户名是否存在
        SysUser user = baseMapper.selectByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }

        String password = AesPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(password);

        entity.setAvatar(defaultAvatar);

        // 保存用户
        baseMapper.insert(entity);
    }

    @Override
    public void update(SysUserBaseDTO dto) {
        SysUser entity = SysUserConverter.INSTANCE.convert(dto);

        // 判断用户名是否存在
        SysUser user = baseMapper.selectByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }

        // 更新用户
        entity.setId(sysCacheService.getUserId());
        updateById(entity);
    }


    @Override
    public void updateAvatar(MultipartFile file) {
        SysUser entity = new SysUser();
        int userId = sysCacheService.getUserId();
        String avatar = fileUtil.saveAvatarImage(file, userId);

        entity.setId(userId);
        entity.setAvatar(avatar);
        updateById(entity);
    }

    @Override
    public void updateBackground(MultipartFile file) {
        SysUser entity = new SysUser();
        int userId = sysCacheService.getUserId();
        String background = fileUtil.saveBackgroundImage(file, userId);

        entity.setId(userId);
        entity.setBackground(background);
        updateById(entity);
    }

    @Override
    public void updatePassword(SysUserPasswordDTO dto) {
        String password = AesPasswordEncoder.encode(dto.getPassword());
        String newPassword = AesPasswordEncoder.encode(dto.getNewPassword());
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
    public SysUserVO getUser() {
        SysUser user = getById(sysCacheService.getUserId());
        return SysUserConverter.INSTANCE.convert(user);
    }
}
