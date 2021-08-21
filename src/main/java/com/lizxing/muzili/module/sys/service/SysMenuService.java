package com.lizxing.muzili.module.sys.service;

import com.lizxing.muzili.module.sys.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据userId获取权限
     *
     * @param userId 用户id
     * @return Set<String>
     */
    Set<String> getPermsByUserId(long userId);

    /**
     * 根据userId获取用户菜单列表
     */
    List<SysMenu> getMenuListByUserId(Long userId);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenu> getMenuListByParentId(Long parentId);

    /**
     * 获取菜单
     *
     * @return List<SysMenu>
     */
    List<SysMenu> getMenuListExceptButton();

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    void removeMenu(long menuId);
}
