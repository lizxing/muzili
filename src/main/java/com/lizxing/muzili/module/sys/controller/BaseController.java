package com.lizxing.muzili.module.sys.controller;

import com.lizxing.muzili.module.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公共控制器
 * @author lizxing
 * @date 2021/8/12
 */
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }
}
