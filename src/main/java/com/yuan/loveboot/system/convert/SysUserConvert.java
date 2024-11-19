package com.yuan.loveboot.system.convert;

import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.vo.SysUserProfileVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import com.yuan.loveboot.utils.ConverterUtil;
import com.yuan.loveboot.utils.FileUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring", uses = {ConverterUtil.class, FileUtil.class})
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "int2Integer")
    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "imageAsBase64")
    SysUserVO convert(SysUser entity);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "integer2Int")
    SysUser convert(SysUserDTO dto);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "int2Integer")
    SysUser convert(SysUserBaseDTO dto);

    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "imageAsBase64")
    SysUserProfileVO convertProfile(SysUser entity);

    List<SysUserProfileVO> convertProfile(List<SysUser> list);
}
