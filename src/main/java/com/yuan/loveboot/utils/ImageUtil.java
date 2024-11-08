package com.yuan.loveboot.utils;

import java.time.Instant;

/**
 * 图片工具
 *
 * @author Maverick
 */
public class ImageUtil {
    /**
     * 生成图片名
     *
     * @param prefix    图片名前缀
     * @param month     月份
     * @param userId    用户编号
     * @param extension 文件扩展名
     * @return 包含时间戳的图片名
     */
    public static String generateImageName(String prefix, String month, Integer userId, String extension) {
        long timestamp = Instant.now().getEpochSecond();
        return String.format("%s_%s_%d_%d.%s", prefix, month, userId, timestamp, extension);
    }
}
