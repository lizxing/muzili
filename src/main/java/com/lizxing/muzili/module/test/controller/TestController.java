package com.lizxing.muzili.module.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizxing
 * @date 2021/8/12
 */
@Api(tags = "测试")
@RequestMapping("/test")
@RestController
public class TestController {

    @ApiOperation(value = "获取当前版本", httpMethod = "GET")
    @GetMapping("/version")
    public String version(){
        return "1.0";
    }
}
