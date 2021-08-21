package com.lizxing.muzili.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.common.annotation.SysLog;
import com.lizxing.muzili.common.constant.SysConstant;
import com.lizxing.muzili.common.util.PageUtils;
import com.lizxing.muzili.common.util.Result;
import com.lizxing.muzili.module.sys.entity.SysRole;
import com.lizxing.muzili.module.sys.service.SysRoleMenuService;
import com.lizxing.muzili.module.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizxing
 * @date 2021/8/13
 */
@Api(tags = "角色")
@RequestMapping("/sys/role")
@RestController
public class SysRoleController extends BaseController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;


    @ApiOperation(value = "选择角色")
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Result select(){
        List<SysRole> list = sysRoleService.listByMap(null);
        return Result.ok().data("list", list);
    }


    @ApiOperation(value = "获取角色列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Result list(int page, int limit, String roleName){
        Page<SysRole> pageParam = new Page<>(page,limit);
        IPage<SysRole> sysRolePage = sysRoleService.listRole(pageParam, roleName);
        return Result.ok().data("page", PageUtils.getData(sysRolePage));
    }

    @SysLog("新增角色")
    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Result save(@RequestBody SysRole role){
        role.setCreateUserId(getUserId());
        sysRoleService.saveRole(role);
        return Result.ok();
    }

    @SysLog("删除角色")
    @ApiOperation(value = "删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public Result delete(@RequestBody Long[] roleIds){
        sysRoleService.removeRole(roleIds);
        return Result.ok();
    }


    @ApiOperation(value = "角色信息")
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Result info(@PathVariable("roleId") Long roleId){
        SysRole sysRole = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.getMenuIdList(roleId);
        sysRole.setMenuIdList(menuIdList);

        return Result.ok().data("role", sysRole);
    }
}
