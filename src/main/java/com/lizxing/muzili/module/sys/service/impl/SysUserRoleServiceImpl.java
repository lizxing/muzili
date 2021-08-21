package com.lizxing.muzili.module.sys.service.impl;

import com.lizxing.muzili.module.sys.entity.SysUserRole;
import com.lizxing.muzili.module.sys.dao.SysUserRoleDao;
import com.lizxing.muzili.module.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Override
    public List<Long> getRoleIdListByUserId(Long userId) {
        return sysUserRoleDao.selectRoleIdListByUserId(userId);
    }
}
