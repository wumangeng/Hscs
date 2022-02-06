package com.hscs.service;

import com.hscs.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-11
 */
public interface IUserService extends IService<User> {
    /**从数据库中取出用户信息*/
    User selectByUsername(String username);
}
