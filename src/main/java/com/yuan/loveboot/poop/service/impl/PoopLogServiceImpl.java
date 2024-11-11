package com.yuan.loveboot.poop.service.impl;

import com.yuan.loveboot.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.poop.dao.PoopLogDao;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.service.PoopLogService;
import com.yuan.loveboot.utils.YearMonthRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 便便记录
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class PoopLogServiceImpl extends BaseServiceImpl<PoopLogDao, PoopLog> implements PoopLogService {
    @Override
    public List<PoopSummary> countUserPoopMonthly(YearMonthRange range) {
        return baseMapper.countUserPoopMonthly(range);
    }
}
