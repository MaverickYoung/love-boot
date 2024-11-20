package com.yuan.loveboot.system.converter;

import com.yuan.loveboot.common.utils.BaseConverter;
import com.yuan.loveboot.common.utils.FileUtil;
import com.yuan.loveboot.system.dto.SysUserBaseDTO;
import com.yuan.loveboot.system.dto.SysUserDTO;
import com.yuan.loveboot.system.po.SysUser;
import com.yuan.loveboot.system.vo.SysUserProfileVO;
import com.yuan.loveboot.system.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring", uses =  FileUtil.class)
public interface SysUserConverter extends BaseConverter {
    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "imageAsBase64")
    SysUserVO convert(SysUser entity);

    SysUser convert(SysUserDTO dto);

    SysUser convert(SysUserBaseDTO dto);

    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "imageAsBase64")
    SysUserProfileVO convertProfile(SysUser entity);

    List<SysUserProfileVO> convertProfile(List<SysUser> list);
}
