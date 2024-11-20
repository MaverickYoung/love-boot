package com.yuan.loveboot.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yuan.loveboot.system.service.SysCacheService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus 自动填充字段
 *
 * @author Maverick
 */
@RequiredArgsConstructor
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";
    private final static String VERSION = "version";
    private final static String DELETED = "isDeleted";
    private final SysCacheService sysCacheService;

    @Override
    public void insertFill(MetaObject metaObject) {
        Integer userId = sysCacheService.getUserId();

        LocalDateTime now = LocalDateTime.now();

        // 用户字段填充
        // 创建者
        setFieldValByName(CREATOR, userId, metaObject);
        // 更新者
        setFieldValByName(UPDATER, userId, metaObject);

        // 创建时间
        setFieldValByName(CREATE_TIME, now, metaObject);
        // 更新时间
        setFieldValByName(UPDATE_TIME, now, metaObject);
        // 版本号
        setFieldValByName(VERSION, 0, metaObject);
        // 删除标识
        setFieldValByName(DELETED, false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新者
        setFieldValByName(UPDATER, sysCacheService.getUserId(), metaObject);
        // 更新时间
        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
    }
}