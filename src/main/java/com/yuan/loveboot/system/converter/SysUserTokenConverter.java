package com.yuan.loveboot.system.converter;

import com.yuan.loveboot.system.po.SysUserToken;
import com.yuan.loveboot.system.vo.SysUserTokenVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户Token
 *
 * @author Maverick
 */
@Mapper
public interface SysUserTokenConverter {
    SysUserTokenConverter INSTANCE = Mappers.getMapper(SysUserTokenConverter.class);

    SysUserToken convert(SysUserTokenVO vo);

    SysUserTokenVO convert(SysUserToken entity);

}