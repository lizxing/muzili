package com.lizxing.muzili.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lizxing.muzili.module.sys.entity.SysUser;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 分页查询角色列表
     *
     * @param pageParam 分页参数
     * @param roleName 角色名称
     * @return IPage<SysRole>
     */
    IPage<SysRole> listRole(Page<SysRole> pageParam, String roleName);

    /**
     * 新增角色
     *
     * @param sysRole 角色
     */
    void saveRole(SysRole sysRole);

    /**
     * 删除角色
     *
     * @param roleIds 角色ids
     */
    void removeRole(Long[] roleIds);
}
