package com.hscs.mapper;

import com.hscs.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

}
