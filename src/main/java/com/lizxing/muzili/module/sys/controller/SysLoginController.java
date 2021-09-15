package com.lizxing.muzili.module.sys.controller;

import com.lizxing.muzili.common.annotation.SysLog;
import com.lizxing.muzili.common.util.Result;
import com.lizxing.muzili.module.sys.dto.SysLoginParam;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.lizxing.muzili.module.sys.entity.SysUserToken;
import com.lizxing.muzili.module.sys.service.SysCaptchaService;
import com.lizxing.muzili.module.sys.service.SysUserService;
import com.lizxing.muzili.module.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author lizxing
 * @date 2021/8/12
 */
@Api(tags = "系统")
@RestController
@RequestMapping("/sys")
public class SysLoginController extends BaseController{

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserTokenService sysUserTokenService;

    @Autowired
    SysCaptchaService sysCaptchaService;


    /**
     * 验证码
     */
    @ApiOperation(value = "登录")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }


    @SysLog("登录")
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody SysLoginParam sysLoginParam) {

        // 为方便测试 =0直接放行
        boolean captcha = sysCaptchaService.validate(sysLoginParam.getUuid(), sysLoginParam.getCaptcha());
        if (!captcha) {
            return Result.failed("验证码不正确");
        }


        // 用户信息
        SysUser sysUser = sysUserService.getByUsername(sysLoginParam.getUsername());

        // 账号不存在、密码错误
        if(sysUser == null || !sysUser.getPassword().equals(new Sha256Hash(sysLoginParam.getPassword(), sysUser.getSalt()).toHex())) {
            return Result.failed("账号或密码不正确");
        }

        // 账号锁定
        if(sysUser.getStatus() == 0){
            return Result.failed("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        SysUserToken sysUserToken = sysUserTokenService.createToken(sysUser.getUserId());
        return Result.ok().data("token", sysUserToken.getToken()).data("expire", sysUserToken.getExpireTime());
    }

    /**
     * 登出
     */
    @SysLog("登出")
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Result logout() {
        sysUserTokenService.clearToken(getUserId());
        return Result.ok();
    }
}
