package com.yuan.loveboot.system.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.loveboot.mybatis.dao.BaseDao;
import com.yuan.loveboot.system.entiy.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户
 *
 * @author Maverick
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUser> {

    SysUser getById(@Param("id") Integer id);

    default SysUser getByUsername(String username) {
        return this.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }
}