package com.lizxing.muzili.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.lizxing.muzili.common.exception.MException;
import com.lizxing.muzili.module.sys.entity.SysCaptcha;
import com.lizxing.muzili.module.sys.dao.SysCaptchaDao;
import com.lizxing.muzili.module.sys.service.SysCaptchaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统验证码 服务实现类
 * </p>
 *
 * @author lizxing
 * @since 2021-08-21
 */
@Service
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptcha> implements SysCaptchaService {

    @Autowired
    private Producer producer;

    @Autowired
    SysCaptchaDao sysCaptchaDao;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new MException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptcha captcha = new SysCaptcha();
        captcha.setUuid(uuid);
        captcha.setCode(code);
        //5分钟后过期
        captcha.setExpireTime(LocalDateTime.now().plusMinutes(5));
        sysCaptchaDao.insert(captcha);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        SysCaptcha sysCaptcha = this.getOne(new QueryWrapper<SysCaptcha>().eq("uuid", uuid));
        if(sysCaptcha == null){
            return false;
        }

        // 删除验证码
        this.removeById(uuid);

        // 为方便测试 =0直接放行
        String zero = "0";
        if (zero.equals(code)){
            return true;
        }

        return sysCaptcha.getCode().equalsIgnoreCase(code) && sysCaptcha.getExpireTime().isAfter(LocalDateTime.now());
    }
}
