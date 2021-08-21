package com.lizxing.muzili.module.sys.dto;

import lombok.Data;

/**
 * 登录请求参数
 * @author lizxing
 * @date 2021/8/12
 */
@Data
public class SysLoginParam {
    private String username;
    private String password;
    private String captcha;
    private String uuid;
}
