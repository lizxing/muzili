package com.lizxing.muzili.module.sys.service;

import com.lizxing.muzili.module.sys.entity.SysUserToken;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户Token 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
public interface SysUserTokenService extends IService<SysUserToken> {

    /**
     * 通过accessToken查询用户token信息
     * @param accessToken 传入token
     * @return SysUserToken
     */
    SysUserToken getUserTokenByToken(String accessToken);

    /**
     * 生成token
     * @param userId 用户ID
     * @return Result
     */
    SysUserToken createToken(long userId);

    /**
     * 清空token
     * @param userId
     */
    void clearToken(long userId);
}
