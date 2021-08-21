package com.lizxing.muzili.common.annotation;

import java.lang.annotation.*;

/**
 * @author lizxing
 * @date 2021/8/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
