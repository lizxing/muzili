package com.lizxing.muzili.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-18
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 查询日志列表
     * @param pageParam 分页参数
     * @param username 用户名
     * @return IPage<SysLog>
     */
    IPage<SysLog> listLog(Page<SysLog> pageParam, String username);
}
