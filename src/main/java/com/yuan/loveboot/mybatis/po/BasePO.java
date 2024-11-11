package com.yuan.loveboot.mybatis.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity基类
 *
 * @author Maverick
 */
@Data
public abstract class BasePO {
    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;
}
