package com.lizxing.muzili.module.sys.dao;

import com.lizxing.muzili.module.sys.entity.SysUserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统用户Token Mapper 接口
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysUserTokenDao extends BaseMapper<SysUserToken> {

    @Select("select * from sys_user_token where token = #{token}")
    SysUserToken selectByToken(String tokan);

}
