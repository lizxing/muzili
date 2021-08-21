package com.lizxing.muzili.module.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizxing.muzili.module.sys.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizxing.muzili.module.sys.entity.SysUser;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysRoleDao extends BaseMapper<SysRole> {
    @Select("select * from sys_role where role_name like concat('%',#{roleName},'%')")
    Page<SysRole> selectRoleList(Page<SysRole> pageParam, String roleName);

}
