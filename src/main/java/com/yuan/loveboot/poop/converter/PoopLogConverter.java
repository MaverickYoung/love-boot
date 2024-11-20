package com.yuan.loveboot.poop.converter;

import com.yuan.loveboot.common.utils.BaseConverter;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.vo.PoopLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
@Mapper
public interface PoopLogConverter extends BaseConverter {
    PoopLogConverter INSTANCE = Mappers.getMapper(PoopLogConverter.class);

    PoopLogVO convert(PoopLog entity);

    List<PoopLogVO> convert(List<PoopLog> list);

}