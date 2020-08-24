package com.example.demo.utils;

import com.example.demo.aop.SectionAspect;
import com.example.demo.interfaces.SectionAdvice;
import com.example.demo.interfaces.SectionProxyMethodInvocation;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: XF
 * @Date: 2020/8/20 10:29
 * 后置处理方法
 */
@Data
public class LaterAdvice implements SectionAdvice {

    private Class<?> declaringClass;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Method aspectMethod;

    public LaterAdvice(Method method){
        this.declaringClass = method.getDeclaringClass();
        this.methodName = method.getName();
        this.parameterTypes = method.getParameterTypes();
        this.aspectMethod = method;
    }

    @Override
    public Object invoke(SectionAspect sectionAspect, SectionProxyMethodInvocation sectionProxyMethodInvocation) throws Throwable {
        Object obj;
        try {
            aspectMethod.setAccessible(true);
            obj = sectionProxyMethodInvocation.proceed();
        }  finally {
            aspectMethod.invoke(sectionAspect,parameterTypes);
        }
        return obj;
    }
}
