package com.yuan.loveboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.common.exception.ServerException;
import com.yuan.loveboot.common.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.system.dao.SysUserGroupDao;
import com.yuan.loveboot.system.po.SysUserGroup;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.system.service.SysUserGroupService;
import com.yuan.loveboot.system.service.SysUserService;
import com.yuan.loveboot.system.vo.SysUserProfileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户分组
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserGroupServiceImpl extends BaseServiceImpl<SysUserGroupDao, SysUserGroup> implements SysUserGroupService {
    private final SysCacheService sysCacheService;
    private final SysUserService sysUserService;

    @Override
    public void save(int otherUserId) {
        SysUserGroup sysUserGroup = new SysUserGroup();
        int userId = sysCacheService.getUserId();

        if (otherUserId == userId) {
            throw new ServerException("不能绑定自己");
        }

        checkUserBinding(userId, "已绑定搭子");
        checkUserBinding(otherUserId, otherUserId + "已绑定搭子");

        sysUserGroup.setUser1Id(userId);
        sysUserGroup.setUser2Id(otherUserId);

        baseMapper.insert(sysUserGroup);
    }

    @Override
    public void delete() {
        int userId = sysCacheService.getUserId();
        LambdaQueryWrapper<SysUserGroup> query = Wrappers.lambdaQuery();
        query.eq(SysUserGroup::getUser1Id, userId)
                .or()
                .eq(SysUserGroup::getUser2Id, userId);
        baseMapper.delete(query);
    }

    /**
     * 检查用户是否绑定分组
     *
     * @param userId       用户 ID
     * @param errorMessage 错误提示
     */
    private void checkUserBinding(int userId, String errorMessage) {
        LambdaQueryWrapper<SysUserGroup> query = Wrappers.lambdaQuery();
        query.eq(SysUserGroup::getUser1Id, userId)
                .or()
                .eq(SysUserGroup::getUser2Id, userId);

        if (!baseMapper.selectList(query).isEmpty()) {
            throw new ServerException(errorMessage);
        }
    }

    @Override
    public Integer selectOtherUserId() {
        int userId = sysCacheService.getUserId();
        LambdaQueryWrapper<SysUserGroup> query = Wrappers.lambdaQuery();
        query.eq(SysUserGroup::getUser1Id, userId)
                .or()
                .eq(SysUserGroup::getUser2Id, userId);
        SysUserGroup sysUserGroup = baseMapper.selectOne(query);

        if (sysUserGroup == null) {
            return null;
        }
        return sysUserGroup.getUser1Id() == userId ? sysUserGroup.getUser2Id() : sysUserGroup.getUser1Id();

    }

    @Override
    public SysUserProfileVO fetchOtherUserProfile() {
        int userId = sysCacheService.getUserId();
        List<SysUserProfileVO> profileVOS = sysUserService.findByIds(List.of(userId));

        return profileVOS.isEmpty() ? null : profileVOS.getFirst();
    }
}
