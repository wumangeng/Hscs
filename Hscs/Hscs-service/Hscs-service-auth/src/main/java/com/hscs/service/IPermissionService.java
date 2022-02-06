package com.hscs.service;

import com.alibaba.fastjson.JSONObject;
import com.hscs.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-03-11
 */
public interface IPermissionService extends IService<Permission> {
    /**获取全部菜单*/
    List<Permission> queryAllMenu();

    /**根据角色获取菜单*/
    List<Permission> selectAllMenu(String roleId);

    /**给角色分配权限*/
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    /**递归删除菜单*/
    void removeChildById(String id);

    /**根据用户id获取用户菜单*/
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);



    /**获取全部菜单*/
    List<Permission> queryAllMenus();

    /**递归删除菜单*/
    void removeChildByIds(String id);

    /**给角色分配权限*/
    void saveRolePermissionRealtionShips(String roleId, String[] permissionId);
}
