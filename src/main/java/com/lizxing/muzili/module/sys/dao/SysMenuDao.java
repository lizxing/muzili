package com.lizxing.muzili.module.sys.dao;

import com.lizxing.muzili.module.sys.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    @Select("select * from sys_menu where parent_id = #{parentId} order by order_num asc")
    List<SysMenu> selectMenuListParentId(long parentId);

    @Select("select distinct rm.menu_id from sys_user_role ur \n" +
            "LEFT JOIN sys_role_menu rm on ur.role_id = rm.role_id \n" +
            "where ur.user_id = #{userId}")
    List<Long> selectMenuIdByUserId(long userId);

    @Select("select * from sys_menu where type != 2 order by order_num asc")
    List<SysMenu> selectMenuListExceptButton();
}
