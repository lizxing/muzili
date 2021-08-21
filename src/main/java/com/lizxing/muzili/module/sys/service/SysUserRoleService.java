package com.lizxing.muzili.module.sys.service;

import com.lizxing.muzili.module.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户id获取角色id列表
     * @param userId 用户id
     * @return List<Long>
     */
    List<Long> getRoleIdListByUserId(Long userId);
}
