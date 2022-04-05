package com.lyj.vblog.common.aop;

import java.lang.annotation.*;


/**
 * 用于缓存
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000; // 缓存的过期时间 默认1分钟

    String name() default "";
}
