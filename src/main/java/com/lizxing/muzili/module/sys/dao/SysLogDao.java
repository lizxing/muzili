package com.lizxing.muzili.module.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizxing.muzili.module.sys.entity.SysUser;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-18
 */
public interface SysLogDao extends BaseMapper<SysLog> {

    @Select("select * from sys_log where username like concat('%',#{username},'%')")
    IPage<SysLog> selectLogList(Page<SysLog> pageParam, String username);

}
