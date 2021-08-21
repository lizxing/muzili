package com.lizxing.muzili.module.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username = #{username}")
    SysUser selectByUsername(String username);

    @Select("select * from sys_user where status = 1 and username like concat('%',#{username},'%')")
    Page<SysUser> selectUserList(Page<SysUser> pageParam, String username);

}
