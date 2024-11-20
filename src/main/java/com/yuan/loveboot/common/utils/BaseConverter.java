package com.yuan.loveboot.common.utils;

import org.mapstruct.Mapper;

/**
 * 基础转换器
 *
 * @author Maverick
 */
@Mapper(componentModel = "spring")
public interface BaseConverter {
    // 处理 Boolean 到 boolean 的映射
    default boolean mapBooleanToPrimitive(Boolean value) {
        return value != null && value;  // null 转为 false
    }
}
