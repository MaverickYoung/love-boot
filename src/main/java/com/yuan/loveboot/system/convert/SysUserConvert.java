package com.yuan.loveboot.system.convert;

import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVO convert(SysUser entity);

    SysUser convert(SysUserDTO dto);

    SysUser convert(SysUserBaseDTO dto);
}
