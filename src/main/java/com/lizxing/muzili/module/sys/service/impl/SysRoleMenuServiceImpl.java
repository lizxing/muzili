package com.lizxing.muzili.module.sys.service.impl;

import com.lizxing.muzili.module.sys.entity.SysRoleMenu;
import com.lizxing.muzili.module.sys.dao.SysRoleMenuDao;
import com.lizxing.muzili.module.sys.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<Long> getMenuIdList(Long roleId) {
        return sysRoleMenuDao.selectMenuIdListByRoleId(roleId);
    }
}
