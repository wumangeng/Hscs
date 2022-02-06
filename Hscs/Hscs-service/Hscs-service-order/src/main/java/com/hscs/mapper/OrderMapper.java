package com.hscs.mapper;

import com.hscs.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
