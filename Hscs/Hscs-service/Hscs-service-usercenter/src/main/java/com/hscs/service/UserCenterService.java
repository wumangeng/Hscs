package com.hscs.service;

import com.hscs.entity.UserCenter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hscs.entity.vo.RegisterVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-04
 */
public interface UserCenterService extends IService<UserCenter> {

    /**登录*/
    String login(UserCenter user);

    /**注册*/
    void register(RegisterVO registerVO);

    /**根据openid判断*/
    UserCenter getOpenIdUser(String openid);

    /**查询某一天注册人数*/
    Integer countRegisterDay(String day);
}
