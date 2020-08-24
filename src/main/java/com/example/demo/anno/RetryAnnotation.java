package com.example.demo.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: XF
 * @Date: 2020/7/22 10:22
 * 乐观锁更新失败自动重试注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryAnnotation {
    public int retry() default 3; //默认重试次数
}
