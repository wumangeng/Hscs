package com.hscs.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hscs.commonutils.JwtInfo;
import com.hscs.commonutils.JwtUtils;
import com.hscs.commonutils.R;
import com.hscs.entity.Order;
import com.hscs.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-03-08
 */
@Api("订单 前端控制器")
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @ApiOperation(value = "生成订单")
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String userId = JwtUtils.getuserIdByJwtToken(request);
        String orderNo = orderService.createOrders(courseId, userId);
        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据订单id查询订单信息
     */
    @ApiOperation(value = "根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }


    /**
     * 查询订单表中订单的状态 判断课程是否购买
     */
    @ApiOperation( "判断课程是否购买")
    @GetMapping("isBought/{courseId}")
    public R isBuyByCourseId(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBought = orderService.isBoughtByCourseId(courseId, JwtUtils.getMemberIdByJwtToken(request).getId());
        return R.ok().data("isBought", isBought);
    }

    @ApiOperation(value = "获取当前用户订单列表")
    @GetMapping("orderList")
    public R list(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<Order> list = orderService.selectByMemberId(jwtInfo.getId());
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("removeOrder/{orderId}")
    public R remove(@PathVariable String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = orderService.removeById(orderId, jwtInfo.getId());
        if(result){
            return R.ok().message("删除订单成功");
        }else{
            return R.error().message("数据不存在");
        }
    }
}
