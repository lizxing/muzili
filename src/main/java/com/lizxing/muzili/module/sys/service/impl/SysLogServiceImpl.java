package com.lizxing.muzili.module.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysLog;
import com.lizxing.muzili.module.sys.dao.SysLogDao;
import com.lizxing.muzili.module.sys.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-18
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements SysLogService {

    @Autowired
    SysLogDao sysLogDao;

    @Override
    public IPage<SysLog> listLog(Page<SysLog> pageParam, String username) {
        return sysLogDao.selectLogList(pageParam, username);
    }
}
