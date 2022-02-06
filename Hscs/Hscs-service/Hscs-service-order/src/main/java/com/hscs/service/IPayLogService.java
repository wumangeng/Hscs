package com.hscs.service;

import com.hscs.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
public interface IPayLogService extends IService<PayLog> {

    /**生成微信支付二维码接口*/
    Map createQrCode(String orderNo);

    /**添加记录到支付表,更新订单表订单状态*/
    void updateOrdersStatus(Map<String, String> map);

    /**根据订单号查询 支付状态*/
    Map<String, String> queryPayStatus(String orderNo);
}
