package com.lizxing.muzili.module.sys.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.dao.SysUserRoleDao;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.lizxing.muzili.module.sys.dao.SysUserDao;
import com.lizxing.muzili.module.sys.entity.SysUserRole;
import com.lizxing.muzili.module.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserDao.selectByUsername(username);
    }

    @Override
    public IPage<SysUser> listUser(Page<SysUser> pageParam, String username) {
        return sysUserDao.selectUserList(pageParam, username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(SysUser sysUser) {
        // 保存用户
        sysUser.setCreateTime(LocalDateTime.now());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(sysUser.getPassword(), salt).toHex());
        sysUser.setSalt(salt);
        sysUserDao.insert(sysUser);

        // 清空旧的用户-角色关系
        sysUserRoleDao.deleteByUserId(sysUser.getUserId());

        // 建立新的用户-角色关系
        for(Long roleId : sysUser.getRoleIdList()){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleDao.insert(sysUserRole);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeUsers(Long[] userIds) {
        // 删除用户
        removeByIds(Arrays.asList(userIds));
        // 删除用户-角色绑定关系
        sysUserRoleDao.deleteByUserIds((List<String>)Convert.toList(userIds));
    }
}
