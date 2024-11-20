package com.yuan.loveboot.poop.convert;

import com.yuan.loveboot.common.utils.FileUtil;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.vo.PoopRewardVO;
import com.yuan.loveboot.poop.vo.PoopSummaryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
@Mapper(componentModel = "spring", uses = FileUtil.class)
public interface PoopSummaryConvert {
    PoopSummaryConvert INSTANCE = Mappers.getMapper(PoopSummaryConvert.class);

    PoopSummaryVO convert(PoopSummary entity);
    List<PoopSummaryVO> convert(List<PoopSummary> list);

    @Mapping(target = "rewardImage", source = "rewardImage", qualifiedByName = "imageAsBase64")
    PoopRewardVO convertReward(PoopSummary entity);
    List<PoopRewardVO> convertReward(List<PoopSummary> list);

}