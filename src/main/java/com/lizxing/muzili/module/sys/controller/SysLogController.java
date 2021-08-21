package com.lizxing.muzili.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.common.util.PageUtils;
import com.lizxing.muzili.common.util.Result;
import com.lizxing.muzili.module.sys.entity.SysLog;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.lizxing.muzili.module.sys.service.SysLogService;
import com.lizxing.muzili.module.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lizxing
 * @date 2021/8/19
 */
@Api(tags = "日志")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    @Autowired
    SysUserService sysUserService;

    @ApiOperation(value = "获取日志列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    public Result list(int page, int limit, String username){
        Page<SysLog> pageParam = new Page<>(page,limit);
        IPage<SysLog> sysLogPage = sysLogService.listLog(pageParam, username);
        return Result.ok().data("page", PageUtils.getData(sysLogPage));
    }
}
