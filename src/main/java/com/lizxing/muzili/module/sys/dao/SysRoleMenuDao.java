package com.lizxing.muzili.module.sys.dao;

import com.lizxing.muzili.module.sys.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {
    @Select("delete from sys_role_menu where role_id = #{roleId}")
    void deleteByRoleId(long roleId);

    @Delete("<script>" +
            "delete from sys_role_menu where role_id in " +
            "   <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "       #{item}" +
            "   </foreach>" +
            "</script>")
    void deleteByRoleIds(List<String> ids);

    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Long> selectMenuIdListByRoleId(Long roleId);

    @Select("delete from sys_role_menu where menu_id = #{menuId}")
    void deleteByMenuId(long menuId);
}
