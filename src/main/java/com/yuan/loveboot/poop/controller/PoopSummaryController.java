package com.yuan.loveboot.poop.controller;

import com.yuan.loveboot.exception.ServerException;
import com.yuan.loveboot.poop.dto.RewardDTO;
import com.yuan.loveboot.poop.service.PoopSummaryService;
import com.yuan.loveboot.poop.vo.UserStatsVO;
import com.yuan.loveboot.utils.Result;
import com.yuan.loveboot.utils.YearMonthRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
@RestController
@RequestMapping("poop/summary")
@RequiredArgsConstructor
@Tag(name = "便便核算")
public class PoopSummaryController {
    private final PoopSummaryService poopSummaryService;


    @GetMapping("list")
    @Operation(summary = "获取用户的便便统计信息")
    public Result<List<UserStatsVO>> getPoopSummaryByMonth(@RequestBody YearMonthRange range) {
        List<UserStatsVO> list = poopSummaryService.findPoopSummaryByMonth(range);

        return Result.ok(list);
    }

    @PostMapping("reward")
    @Operation(summary = "更新奖励")
    public Result<String> updateReward(@RequestBody @Valid RewardDTO dto) {
        // 解析 YearMonth
        YearMonth month;
        try {
            month = YearMonth.parse(dto.getMonth());
        } catch (DateTimeParseException e) {
            throw new ServerException("时间格式错误，请使用 YYYY-MM");
        }

        // 解码 Base64 图片
        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(dto.getImage());
        } catch (IllegalArgumentException e) {
            throw new ServerException("图片解码失败，请检查 Base64 编码格式");
        }

        poopSummaryService.updateReward(imageBytes, month);

        return Result.ok();
    }
}
