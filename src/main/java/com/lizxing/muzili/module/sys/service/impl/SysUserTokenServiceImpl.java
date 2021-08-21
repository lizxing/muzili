package com.lizxing.muzili.module.sys.service.impl;

import com.lizxing.muzili.common.util.Result;
import com.lizxing.muzili.common.util.TokenGenerator;
import com.lizxing.muzili.module.sys.entity.SysUserToken;
import com.lizxing.muzili.module.sys.dao.SysUserTokenDao;
import com.lizxing.muzili.module.sys.service.SysUserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-12
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserToken> implements SysUserTokenService {

    @Autowired
    SysUserTokenDao sysUserTokenDao;

    @Override
    public SysUserToken getUserTokenByToken(String token) {
        return sysUserTokenDao.selectByToken(token);
    }

    @Override
    public SysUserToken createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(3);

        //判断是否生成过token
        SysUserToken sysUserToken = sysUserTokenDao.selectById(userId);
        if(sysUserToken == null){
            sysUserToken = new SysUserToken();
            sysUserToken.setUserId(userId);
            sysUserToken.setToken(token);
            sysUserToken.setUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);

            sysUserTokenDao.insert(sysUserToken);
        }else{
            sysUserToken.setToken(token);
            sysUserToken.setUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);

            sysUserTokenDao.updateById(sysUserToken);
        }

        return sysUserToken;
    }

    @Override
    public void clearToken(long userId) {
        //修改token为空
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setUserId(userId);
        sysUserToken.setToken("-1");
        sysUserToken.setUpdateTime(LocalDateTime.now());
        updateById(sysUserToken);
    }
}
