package com.lizxing.muzili.module.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lizxing.muzili.common.constant.MenuType;
import com.lizxing.muzili.common.constant.SysConstant;
import com.lizxing.muzili.module.sys.dao.SysRoleMenuDao;
import com.lizxing.muzili.module.sys.dao.SysUserRoleDao;
import com.lizxing.muzili.module.sys.entity.SysMenu;
import com.lizxing.muzili.module.sys.dao.SysMenuDao;
import com.lizxing.muzili.module.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Autowired
    SysMenuDao sysMenuDao;

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Override
    public Set<String> getPermsByUserId(long userId) {
        List<String> permsList;

        if(userId == SysConstant.SUPER_ADMIN){
            // 系统管理员
            List<SysMenu> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            // 其他人员
            permsList = sysUserRoleDao.getPermsByUserId(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public List<SysMenu> getMenuListByUserId(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == SysConstant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysMenuDao.selectMenuIdByUserId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenu> getMenuListByParentId(Long parentId) {
        // syd ggd h
        // kds xwt rms
        return sysMenuDao.selectMenuListParentId(parentId);
    }

    @Override
    public List<SysMenu> getMenuListExceptButton() {
        return sysMenuDao.selectMenuListExceptButton();
    }

    @Override
    public void removeMenu(long menuId) {
        // 删除菜单
        sysMenuDao.deleteById(menuId);
        // 删除角色-菜单关系
        sysRoleMenuDao.deleteByMenuId(menuId);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenu> menuList = getMenuListByParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }


    private List<SysMenu> getMenuListByParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = getMenuListByParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
        List<SysMenu> subMenuList = new ArrayList<>();

        for(SysMenu sysMenu : menuList){
            //目录
            if(sysMenu.getType() == MenuType.CATALOG.getValue()){
                sysMenu.setList(getMenuTreeList(getMenuListByParentId(sysMenu.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(sysMenu);
        }

        return subMenuList;
    }
}
