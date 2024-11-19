package com.yuan.loveboot.poop.convert;

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
public interface PoopLogConvert {
    PoopLogConvert INSTANCE = Mappers.getMapper(PoopLogConvert.class);

    PoopLogVO convert(PoopLog entity);

    List<PoopLogVO> convert(List<PoopLog> list);

}