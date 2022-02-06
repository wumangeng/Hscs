package com.hscs.controller;


import com.hscs.commonutils.R;
import com.hscs.service.IPayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
@Api(description="订单服务相关(二维码)")
@RestController
@RequestMapping("/eduorder/pay-log")
public class PayLogController {
    @Autowired
    private IPayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * 参数是订单号*/
    @ApiOperation(value = "生成微信支付二维码接口")
    @GetMapping("createQrCode/{orderNo}")
    public R createQrCode(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他需要的信息
        Map map = payLogService.createQrCode(orderNo);
        System.out.println("生成微信支付二维码接口-返回二维码map集合----->"+map);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     * 参数：订单号，根据订单号查询 支付状态
     */
    @ApiOperation(value = "查询订单支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("查询订单支付状态-返回二维码map集合----->"+map);
        if(map == null) {
            return R.error().message("支付未完成...");
        }
        //如果返回map里面不为空，通过map获取订单状态
        //支付成功
        if(map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功!");
        }
        return R.ok().code(25000).message("正在支付中...");

    }
}
