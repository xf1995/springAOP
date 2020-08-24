package com.example.demo.interfaces;

import com.example.demo.aop.SectionAspect;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: XF
 * @Date: 2020/8/20 11:41
 */
public interface SectionAdvice {
    public Object invoke(SectionAspect sectionAspect,SectionProxyMethodInvocation sectionProxyMethodInvocation) throws Throwable;
}
