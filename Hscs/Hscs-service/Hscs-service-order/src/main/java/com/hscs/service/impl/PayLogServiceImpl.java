package com.hscs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.hscs.entity.Order;
import com.hscs.entity.PayLog;
import com.hscs.mapper.PayLogMapper;
import com.hscs.service.IOrderService;
import com.hscs.service.IPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hscs.servicebase.exceptionhandler.HscsException;
import com.hscs.utils.ConstantWxUtils;
import com.hscs.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements IPayLogService {

    @Autowired
    private IOrderService orderService;

    /**生成微信支付二维码接口*/
    @Override
    public Map createQrCode(String orderNo) {
        try {
            //1 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);

            //2 使用map设置生成二维码需要参数
            Map m = new HashMap();
            //m.put("appid","wx74862e0dfcf69954");
            m.put("appid", ConstantWxUtils.WX_PAY_APPID);
            //m.put("mch_id", "1558950191");
            m.put("mch_id", ConstantWxUtils.WX_PAY_PARTNER);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //课程标题
            m.put("body", order.getCourseTitle());
            //订单号
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            //支付后回调地址
            m.put("notify_url", ConstantWxUtils.WX_PAY_NOTIFYURL);
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            //client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setXmlParam(WXPayUtil.generateSignedXml(m,ConstantWxUtils.WX_PAY_PARTNERKEY));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            //返回二维码操作状态码
            map.put("result_code", resultMap.get("result_code"));
            //二维码地址
            map.put("code_url", resultMap.get("code_url"));

            return map;
        }catch(Exception e) {
            throw new HscsException(20001,"生成二维码失败");
        }

    }

    /**根据订单号查询 支付状态*/
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", ConstantWxUtils.WX_PAY_APPID);
            m.put("mch_id", ConstantWxUtils.WX_PAY_PARTNER);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,ConstantWxUtils.WX_PAY_PARTNERKEY));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            return null;
        }
    }

    /**添加记录到支付表,更新订单表订单状态*/
    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //更新订单表订单状态
        if(order.getStatus().intValue() == 1) { return; }
        //1代表已经支付
        order.setStatus(1);
        orderService.updateById(order);

        //向支付表添加支付记录
        PayLog payLog = new PayLog();
        //订单号
        payLog.setOrderNo(orderNo);
        //订单完成时间
        payLog.setPayTime(new Date());
        //支付类型 1微信
        payLog.setPayType(1);
        //总金额(分)
        payLog.setTotalFee(order.getTotalFee());

        //支付状态
        payLog.setTradeState(map.get("trade_state"));
        //流水号
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }

}
