package com.lizxing.muzili.module.sys.service;

import com.lizxing.muzili.module.sys.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色id获取菜单id列表
     * @param roleId 角色id
     * @return List<Long>
     */
    List<Long> getMenuIdList(Long roleId);
}
