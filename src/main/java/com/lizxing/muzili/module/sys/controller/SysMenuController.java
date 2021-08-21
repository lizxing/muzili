package com.lizxing.muzili.module.sys.controller;

import com.lizxing.muzili.common.annotation.SysLog;
import com.lizxing.muzili.common.constant.MenuType;
import com.lizxing.muzili.common.exception.MException;
import com.lizxing.muzili.common.util.Result;
import com.lizxing.muzili.module.sys.entity.SysMenu;
import com.lizxing.muzili.module.sys.entity.SysRole;
import com.lizxing.muzili.module.sys.service.SysMenuService;
import com.lizxing.muzili.module.sys.service.SysRoleMenuService;
import com.lizxing.muzili.module.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author lizxing
 * @date 2021/8/12
 */
@Api(tags = "菜单")
@RequestMapping("/sys/menu")
@RestController
public class SysMenuController extends BaseController {

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleService sysRoleService;

    @ApiOperation(value = "获取导航菜单")
    @GetMapping("/nav")
    public Result nav() {
        List<SysMenu> menuList = sysMenuService.getMenuListByUserId(getUserId());
        Set<String> permsSet = sysMenuService.getPermsByUserId(getUserId());
        return Result.ok().data("menuList", menuList).data("permissions", permsSet);
    }


    @ApiOperation(value = "获取导航菜单列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list() {
        List<SysMenu> menuList = sysMenuService.list();
        HashMap<Long, SysMenu> menuMap = new HashMap<>(12);
        for (SysMenu s : menuList) {
            menuMap.put(s.getMenuId(), s);
        }
        for (SysMenu s : menuList) {
            SysMenu parent = menuMap.get(s.getParentId());
            if (Objects.nonNull(parent)) {
                s.setParentName(parent.getName());
            }

        }
        return menuList;
    }



    @ApiOperation(value = "选择菜单")
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.getMenuListExceptButton();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        menuList.add(root);

        return Result.ok().data("menuList", menuList);
    }



    @ApiOperation(value = "获取菜单信息")
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result info(@PathVariable("menuId") Long menuId){
        SysMenu menu = sysMenuService.getById(menuId);
        return Result.ok().data("menu", menu);
    }


    @SysLog("保存菜单")
    @ApiOperation(value = "保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody SysMenu menu) {
        verifyForm(menu);
        sysMenuService.save(menu);
        return Result.ok();
    }


    @SysLog("修改菜单")
    @ApiOperation(value = "修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);
        sysMenuService.updateById(menu);
        return Result.ok();
    }

    @SysLog("删除菜单")
    @ApiOperation(value = "删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public Result delete(@PathVariable("menuId") long menuId){
        if(menuId <= 31){
            return Result.failed("系统菜单，不能删除");
        }

        List<SysMenu> menuList = sysMenuService.getMenuListByParentId(menuId);
        if(menuList.size() > 0){
            return Result.failed("请先删除子菜单或按钮");
        }

        sysMenuService.removeMenu(menuId);
        return Result.ok();
    }


    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        // 查询上级菜单的类型
        int parentType = MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        // 目录、菜单
        if (menu.getType() == MenuType.CATALOG.getValue() ||
                menu.getType() == MenuType.MENU.getValue()) {
            if (parentType != MenuType.CATALOG.getValue()) {
                throw new MException("上级菜单只能为目录类型");
            }
        }

        // 按钮
        if (menu.getType() == MenuType.BUTTON.getValue()) {
            if (parentType != MenuType.MENU.getValue()) {
                throw new MException("上级菜单只能为菜单类型");
            }
        }
    }
}
