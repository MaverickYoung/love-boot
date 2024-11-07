package com.yuan.loveboot.system.convert;

import com.yuan.loveboot.system.entiy.SysUser;
import com.yuan.loveboot.system.vo.SysUserBaseVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserBaseVO convert(SysUser entity);

    SysUser convert(SysUserVO vo);

    SysUser convert(SysUserBaseVO vo);
}
