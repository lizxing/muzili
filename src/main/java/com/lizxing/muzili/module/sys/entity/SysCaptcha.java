package com.lizxing.muzili.module.sys.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统验证码
 * </p>
 *
 * @author lizxing
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysCaptcha对象", description="系统验证码")
public class SysCaptcha implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expireTime;


}
