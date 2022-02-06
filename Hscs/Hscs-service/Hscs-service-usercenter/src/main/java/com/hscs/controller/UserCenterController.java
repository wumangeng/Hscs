package com.hscs.controller;


import com.hscs.commonutils.JwtUtils;
import com.hscs.commonutils.R;
import com.hscs.commonutils.ordervo.UserCenterOrder;
import com.hscs.entity.UserCenter;
import com.hscs.entity.vo.RegisterVO;
import com.hscs.service.UserCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-03-04
 */
@Api("用户控制器")
@RestController
@RequestMapping("/usercenter/user")
public class UserCenterController {
    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation("登录")
    @PostMapping("login")
    public R loginUser(@RequestBody UserCenter user) {
        //user对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = userCenterService.login(user);
        return R.ok().data("token",token);
    }


    @ApiOperation("注册")
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVO RegisterVO) {
        userCenterService.register(RegisterVO);
        return R.ok();
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("getUserInfo")
    public R getuserInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String userId = JwtUtils.getuserIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UserCenter user = userCenterService.getById(userId);
        return R.ok().data("userInfo",user);
    }


    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("getUserInfoOrder/{id}")
    public UserCenterOrder getUserInfoOrder(@PathVariable String id) {
        UserCenter user = userCenterService.getById(id);
        //把user对象里面值复制给UserCenterOrder对象
        UserCenterOrder UserCenterOrder = new UserCenterOrder();
        BeanUtils.copyProperties(user,UserCenterOrder);
        return UserCenterOrder;
    }

    @ApiOperation("查询某一天注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = userCenterService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}
