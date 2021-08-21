package com.lizxing.muzili.module.sys.dto;

import lombok.Data;

/**
 * @author lizxing
 * @date 2021/8/13
 */
@Data
public class SysUserListParam {
    private int page;
    private int limit;
    private String username;
}
