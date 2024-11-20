package com.yuan.loveboot.system.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.loveboot.common.mybatis.dao.BaseDao;
import com.yuan.loveboot.system.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户
 *
 * @author Maverick
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUser> {
    default SysUser selectByUsername(String username) {
        return this.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }
}