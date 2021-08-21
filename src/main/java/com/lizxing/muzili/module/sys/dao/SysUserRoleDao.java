package com.lizxing.muzili.module.sys.dao;

import com.lizxing.muzili.module.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

    @Select("select m.perms from sys_user_role ur \n" +
            "LEFT JOIN sys_role_menu rm on ur.role_id = rm.role_id \n" +
            "LEFT JOIN sys_menu m on rm.menu_id = m.menu_id \n" +
            "where ur.user_id = #{userId}")
    List<String> getPermsByUserId(long userId);

    @Delete("<script>" +
            "delete from sys_user_role where user_id in " +
            "   <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "       #{item}" +
            "   </foreach>" +
            "</script>")
    void deleteByUserIds(List<String> ids);

    @Select("delete from sys_user_role where user_id = #{userId}")
    void deleteByUserId(long userId);

    @Delete("<script>" +
            "delete from sys_user_role where role_id in " +
            "   <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "       #{item}" +
            "   </foreach>" +
            "</script>")
    void deleteByRoleIds(List<String> ids);

    @Select("select role_id from sys_user_role where user_id = #{userId}")
    List<Long> selectRoleIdListByUserId(long userId);
}
