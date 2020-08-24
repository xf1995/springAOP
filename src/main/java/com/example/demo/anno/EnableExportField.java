package com.example.demo.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: XF
 * @Date: 2020/6/5 9:36
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableExportField {
    //列宽
    int colWidth() default (int) ((24 + 0.72) * 256);
    //列名
    String colName() default "";
    //设置get方法
    String getMethod() default "";
    //格式化0,1.....
    String readConverterExp() default "";
}
