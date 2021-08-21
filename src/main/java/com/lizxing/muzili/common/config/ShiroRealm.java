package com.lizxing.muzili.common.config;

import com.lizxing.muzili.module.sys.entity.SysUser;
import com.lizxing.muzili.module.sys.entity.SysUserToken;
import com.lizxing.muzili.module.sys.service.SysMenuService;
import com.lizxing.muzili.module.sys.service.SysUserService;
import com.lizxing.muzili.module.sys.service.SysUserTokenService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author lizxing
 * @date 2021/8/11
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserTokenService sysUserTokenService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysMenuService sysMenuService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser sysUser = (SysUser)principalCollection.getPrimaryPrincipal();
        Long userId = sysUser.getUserId();

        //用户权限列表
        Set<String> permsSet = sysMenuService.getPermsByUserId(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String accessToken = (String) authenticationToken.getPrincipal();

        //根据accessToken，查询用户token信息
        SysUserToken sysUserToken = sysUserTokenService.getUserTokenByToken(accessToken);
        //token失效
        if(sysUserToken == null || sysUserToken.getExpireTime().isBefore(LocalDateTime.now())){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        SysUser sysUser = sysUserService.getById(sysUserToken.getUserId());
        //账号锁定
        if(sysUser.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        return new SimpleAuthenticationInfo(sysUser, accessToken, getName());
    }

    /**
     * 自定义Realm要加入supports
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroToken;
    }
}
