package com.hscs.service;

import com.hscs.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-11
 */
public interface IRoleService extends IService<Role> {
    /**根据用户获取角色数据*/
    Map<String, Object> findRoleByUserId(String userId);

    /**根据用户分配角色*/
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    List<Role> selectRoleByUserId(String id);
}
