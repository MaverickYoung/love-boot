package com.yuan.loveboot.poop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.loveboot.common.enums.ResponseCode;
import com.yuan.loveboot.common.exception.ServerException;
import com.yuan.loveboot.common.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.common.utils.FileUtil;
import com.yuan.loveboot.common.utils.YearMonthRange;
import com.yuan.loveboot.poop.converter.PoopSummaryConverter;
import com.yuan.loveboot.poop.dao.PoopSummaryDao;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.service.PoopLogService;
import com.yuan.loveboot.poop.service.PoopSummaryService;
import com.yuan.loveboot.poop.vo.PoopRewardVO;
import com.yuan.loveboot.poop.vo.PoopSummaryVO;
import com.yuan.loveboot.system.service.SysCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        List<PoopSummary> poopSummaries = poopLogService.countByMonth(new YearMonthRange(lastMonth));

        // 插入或更新
        for (PoopSummary poopSummary : poopSummaries) {
            LambdaQueryWrapper<PoopSummary> query = Wrappers.lambdaQuery();
            query.eq(PoopSummary::getMonth, poopSummary.getMonth())
                    .eq(PoopSummary::getUserId, poopSummary.getUserId());
            PoopSummary entry = baseMapper.selectOne(query);
            if (entry == null) {
                this.save(poopSummary);
            } else {
                poopSummary.setId(entry.getId());
                poopSummary.setWinnerStatus(false);
                this.updateById(poopSummary);
            }
        }
    }

    @Override
    public void updateLastMonthWinners() {
        // 获取上个月的年月，格式为 YYYY-MM
        YearMonth lastMonth = YearMonth.now().minusMonths(1);

        // 查询上个月便便最多的用户
        List<Integer> list = baseMapper.selectUserWithMaxPoopCount(lastMonth.toString());

        if (list.isEmpty()) {
            // 如果没有数据，直接返回
            return;
        }

        // 获取上个月的年月格式化字符串
        String lastMonthStr = lastMonth.toString();

        // 使用 LambdaUpdateWrapper 更新这些用户为获胜者
        LambdaUpdateWrapper<PoopSummary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PoopSummary::getMonth, lastMonthStr)
                .in(PoopSummary::getUserId, list)
                .set(PoopSummary::getWinnerStatus, true);

        // 执行更新
        baseMapper.update(null, updateWrapper);
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncLastMonthPoopStatisticsScheduled() {
        syncLastMonthPoopStatistics();
        updateLastMonthWinners();
    }

    @Override
    public List<PoopSummaryVO> findByMonth(YearMonthRange range) {
        LambdaQueryWrapper<PoopSummary> query = Wrappers.lambdaQuery();
        YearMonth start = range.getStart();
        YearMonth end = range.getEnd();

        if (start != null && start.equals(end)) {
            query.eq(PoopSummary::getMonth, start);
        }
        if (start != null && !start.equals(end)) {
            query.ge(PoopSummary::getMonth, start);
        }
        if (end != null && !end.equals(start)) {
            query.le(PoopSummary::getMonth, end);
        }

        List<PoopSummary> list = baseMapper.selectList(query);
        return PoopSummaryConverter.INSTANCE.convert(list);
    }

    @Override
    public List<PoopRewardVO> findRewardByMonth(YearMonth month) {
        LambdaQueryWrapper<PoopSummary> query = Wrappers.lambdaQuery();
        query.eq(PoopSummary::getMonth, month);
        query.eq(PoopSummary::getWinnerStatus, true);

        List<PoopSummary> list = baseMapper.selectList(query);
        return PoopSummaryConverter.INSTANCE.convertReward(list);
    }

    @Override
    public void updateReward(MultipartFile file, YearMonth month) {
        Integer userId = sysCacheService.getUserId();
        if (userId == null) {
            throw new ServerException(ResponseCode.ACCESS_TOKEN_INVALID);
        }

        LambdaQueryWrapper<PoopSummary> query = Wrappers.lambdaQuery();
        query.eq(PoopSummary::getMonth, month)
                .eq(PoopSummary::getUserId, userId)
                .eq(PoopSummary::getWinnerStatus, true);
        PoopSummary poopSummary = baseMapper.selectOne(query);
        if (poopSummary == null) {
            throw new ServerException("参数异常");
        }

        // 保存奖励图片
        String imageName = fileUtil.saveRewardImage(file, month.toString(), userId);

        // 更新奖励图片和状态
        poopSummary.setRewardImage(imageName);
        poopSummary.setRewardStatus(true);
        updateById(poopSummary);
    }
}
