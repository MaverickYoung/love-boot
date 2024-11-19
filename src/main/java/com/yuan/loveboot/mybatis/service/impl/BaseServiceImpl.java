package com.yuan.loveboot.mybatis.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.loveboot.mybatis.dao.BaseDao;
import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.utils.PageDTO;
import org.apache.commons.lang3.StringUtils;


/**
 * 基础服务类，所有Service都要继承
 *
 * @author Maverick
 */
public class BaseServiceImpl<M extends BaseDao<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 获取分页对象
     *
     * @param dto 分页参数
     */
    protected IPage<T> getPage(PageDTO dto) {
        Page<T> page = new Page<>(dto.getCurrent(), dto.getSize());

        // 排序
        if (!StringUtils.isBlank(dto.getOrder())) {
            if (dto.isAsc()) {
                return page.addOrder(OrderItem.asc(dto.getOrder()));
            } else {
                return page.addOrder(OrderItem.desc(dto.getOrder()));
            }
        }

        return page;
    }

}