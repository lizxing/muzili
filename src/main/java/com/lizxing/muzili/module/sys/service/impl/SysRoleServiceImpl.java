package com.lizxing.muzili.module.sys.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.dao.SysRoleMenuDao;
import com.lizxing.muzili.module.sys.dao.SysUserRoleDao;
import com.lizxing.muzili.module.sys.entity.SysRole;
import com.lizxing.muzili.module.sys.dao.SysRoleDao;
import com.lizxing.muzili.module.sys.entity.SysRoleMenu;
import com.lizxing.muzili.module.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Override
    public IPage<SysRole> listRole(Page<SysRole> pageParam, String roleName) {
        return sysRoleDao.selectRoleList(pageParam, roleName);
    }

    @Override
    public void saveRole(SysRole sysRole) {
        // 保存角色
        sysRole.setCreateTime(LocalDateTime.now());
        sysRoleDao.insert(sysRole);

        // 清空旧的角色-菜单关系
        sysRoleMenuDao.deleteByRoleId(sysRole.getRoleId());

        // 建立新的角色-菜单关系
        for(Long menuId : sysRole.getMenuIdList()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuDao.insert(sysRoleMenu);
        }
    }

    @Override
    public void removeRole(Long[] roleIds) {
        // 删除角色
        removeByIds(Arrays.asList(roleIds));
        // 删除用户-角色关系
        sysUserRoleDao.deleteByRoleIds((List<String>) Convert.toList(roleIds));
        // 删除角色-菜单关系
        sysRoleMenuDao.deleteByRoleIds((List<String>) Convert.toList(roleIds));
    }
}
