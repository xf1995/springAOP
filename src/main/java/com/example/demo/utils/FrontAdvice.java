package com.example.demo.utils;

import com.example.demo.aop.SectionAspect;
import com.example.demo.interfaces.SectionAdvice;
import com.example.demo.interfaces.SectionProxyMethodInvocation;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: XF
 * @Date: 2020/8/20 10:14
 * 前置切面处理方法
 */
@Data
public class FrontAdvice implements SectionAdvice {

    private Class<?> declaringClass;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Method aspectMethod;

    public FrontAdvice(Method method){
        this.declaringClass = method.getDeclaringClass();
        this.methodName = method.getName();
        this.parameterTypes = method.getParameterTypes();
        aspectMethod = method;
    }

    @Override
    public Object invoke(SectionAspect sectionAspect, SectionProxyMethodInvocation sectionProxyMethodInvocation) throws Throwable {
        try {
            aspectMethod.setAccessible(true);
            aspectMethod.invoke(sectionAspect,parameterTypes);
            return sectionProxyMethodInvocation.proceed();
        } catch (IllegalAccessException e) {
            throw e.getCause();
        } catch (InvocationTargetException e) {
           throw  e.getTargetException();
        }
    }
}
