package com.yuan.loveboot.poop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.loveboot.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.poop.dao.PoopSummaryDao;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.service.PoopSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class PoopSummaryServiceImpl extends BaseServiceImpl<PoopSummaryDao, PoopSummary> implements PoopSummaryService {

    @Override
    public void calculateMonthlyPoopCount() {
        // 查询每个月每个用户的便便数量
        LambdaQueryWrapper<PoopLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PoopLog::getUserID, PoopLog::getLogTime)
                .eq(PoopLog::getIsDeleted, false)
                .groupBy(PoopLog::getUserID, PoopLog::getLogTime);

        List<PoopSummary> summaries = baseMapper.selectMonthlyPoopCount(queryWrapper);

        // 批量插入或更新
        this.saveOrUpdateBatch(summaries);
    }

    @Override
    public void updateMonthlyWinners() {

    }

    @Override
    public List<PoopSummary> findMonthlyWinners(String month) {
        return List.of();
    }

    @Override
    public PoopSummary findUserSummaryByMonth(int userId, String month) {
        return null;
    }

    @Override
    public void updateRewardStatus(int userId, String month) {

    }
}
