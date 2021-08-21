package com.lizxing.muzili.module.sys.service;

import com.lizxing.muzili.module.sys.entity.SysCaptcha;
import com.baomidou.mybatisplus.extension.service.IService;

import java.awt.image.BufferedImage;

/**
 * <p>
 * 系统验证码 服务类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-21
 */
public interface SysCaptchaService extends IService<SysCaptcha> {

    /**
     * 获取验证码
     * @param uuid id
     * @return BufferedImage
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证验证码
     * @param uuid id
     * @param code code
     * @return boolean
     */
    boolean validate(String uuid, String code);
}
