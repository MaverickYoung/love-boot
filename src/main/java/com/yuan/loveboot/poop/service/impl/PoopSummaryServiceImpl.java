package com.yuan.loveboot.poop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.enums.ResponseCode;
import com.yuan.loveboot.exception.ServerException;
import com.yuan.loveboot.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.poop.dao.PoopSummaryDao;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.service.PoopLogService;
import com.yuan.loveboot.poop.service.PoopSummaryService;
import com.yuan.loveboot.poop.vo.UserStatsVO;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.utils.FileUtil;
import com.yuan.loveboot.utils.YearMonthRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

/**
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class PoopSummaryServiceImpl extends BaseServiceImpl<PoopSummaryDao, PoopSummary> implements PoopSummaryService {
    private final PoopLogService poopLogService;
    private final SysCacheService sysCacheService;
    private final FileUtil fileUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncLastMonthPoopStatistics() {

        // 获取上个月的年月
        YearMonth lastMonth = YearMonth.now().minusMonths(1);

        // 统计上个月用户便便数量
        List<PoopSummary> poopSummaries = poopLogService.countUserPoopMonthly(new YearMonthRange(lastMonth));

        // 批量插入或更新
        this.saveOrUpdateBatch(poopSummaries);
    }

    @Override
    public void updateLastMonthWinners() {
        // 获取上个月的年月，格式为 YYYY-MM
        YearMonth lastMonth = YearMonth.now().minusMonths(1);

        // 查询上个月便便最多的用户
        List<Integer> list = baseMapper.selectUserWithMaxPoopCount(lastMonth);

        if (list == null) {
            // 如果没有数据，直接返回
            return;
        }

        // 获取上个月的年月格式化字符串
        String lastMonthStr = lastMonth.toString();

        // 使用 LambdaUpdateWrapper 更新这些用户为获胜者
        LambdaUpdateWrapper<PoopSummary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PoopSummary::getMonth, lastMonthStr)
                .in(PoopSummary::getUserId, list)
                .set(PoopSummary::getIsWinner, true);

        // 执行更新
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public List<UserStatsVO> findPoopSummaryByMonth(YearMonthRange range) {
        return baseMapper.selectPoopSummaryByMonth(range);
    }

    @Override
    public void updateReward(byte[] imageBytes, YearMonth month) {
        Integer userId = sysCacheService.getUserId();
        if (userId == null) {
            throw new ServerException(ResponseCode.ACCESS_TOKEN_INVALID);
        }

        LambdaQueryWrapper<PoopSummary> query = Wrappers.lambdaQuery();
        query.eq(PoopSummary::getMonth, month)
                .eq(PoopSummary::getUserId, userId)
                .eq(PoopSummary::getIsWinner, true);
        PoopSummary poopSummary = baseMapper.selectOne(query);
        if (poopSummary == null) {
            throw new ServerException("参数异常");
        }

        // 保存奖励图片
        String imageName = fileUtil.saveRewardImage(imageBytes, month.toString(), userId);

        // 更新奖励图片和状态
        poopSummary.setRewardImage(imageName);
        poopSummary.setIsRewarded(true);
        updateById(poopSummary);
    }
}
