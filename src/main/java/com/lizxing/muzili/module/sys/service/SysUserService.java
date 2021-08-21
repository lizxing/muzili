package com.lizxing.muzili.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return SysUser
     */
    SysUser getByUsername(String username);

    /**
     * 分页查询用户列表
     * @param pageParam 分页参数
     * @param username 用户名
     * @return IPage<SysUser>
     */
    IPage<SysUser> listUser(Page<SysUser> pageParam, String username);

    /**
     * 新增用户
     * @param sysUser 用户
     */
    void saveUser(SysUser sysUser);

    /**
     * 删除用户
     * @param userIds 用户ids
     */
    void removeUsers(Long[] userIds);
}
