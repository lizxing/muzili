package com.lizxing.muzili.common.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lizxing
 * @date 2021/8/12
 */
public class ShiroToken implements AuthenticationToken {

    private String token;

    ShiroToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
