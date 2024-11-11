package com.yuan.loveboot.system.convert;

import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.vo.SysUserVO;
import com.yuan.loveboot.utils.ConverterUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",uses = ConverterUtil.class)
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "int2Integer")
    SysUserVO convert(SysUser entity);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "integer2Int")
    SysUser convert(SysUserDTO dto);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "int2Integer")
    SysUser convert(SysUserBaseDTO dto);
}
