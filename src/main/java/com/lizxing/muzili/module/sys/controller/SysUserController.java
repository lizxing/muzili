package com.lizxing.muzili.module.sys.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.common.annotation.SysLog;
import com.lizxing.muzili.common.util.PageUtils;
import com.lizxing.muzili.common.util.Result;
import com.lizxing.muzili.module.sys.dto.SysUserListParam;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.lizxing.muzili.module.sys.service.SysLogService;
import com.lizxing.muzili.module.sys.service.SysUserRoleService;
import com.lizxing.muzili.module.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author lizxing
 * @date 2021/8/13
 */
@Api(tags = "用户")
@RequestMapping("/sys/user")
@RestController
public class SysUserController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("user", getUser());
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Long userId){
        SysUser user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdListByUserId(userId);
        user.setRoleIdList(roleIdList);

        return Result.ok().data("user", user);
    }

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Result list(int page, int limit, String username){
        Page<SysUser> pageParam = new Page<>(page,limit);
        IPage<SysUser> sysUserPage = sysUserService.listUser(pageParam, username);
        return Result.ok().data("page", PageUtils.getData(sysUserPage));
    }

    @SysLog("新增用户")
    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUser sysUser){
        sysUser.setCreateUserId(getUserId());
        sysUserService.saveUser(sysUser);
        return Result.ok();
    }


    @SysLog("删除用户")
    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Long[] userIds){
        if(ArrayUtil.contains(userIds, 1L)){
            return Result.failed("系统管理员不能删除");
        }
        if(ArrayUtil.contains(userIds, getUserId())){
            return Result.failed("当前用户不能删除");
        }
        sysUserService.removeUsers(userIds);
        return Result.ok();
    }

}
