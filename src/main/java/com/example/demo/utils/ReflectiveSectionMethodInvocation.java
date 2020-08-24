package com.example.demo.utils;

import com.example.demo.aop.SectionAspect;
import com.example.demo.interfaces.SectionAdvice;
import com.example.demo.interfaces.SectionProxyMethodInvocation;
import org.springframework.aop.AopInvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @Author: XF
 * @Date: 2020/8/21 14:16
 */
public class ReflectiveSectionMethodInvocation implements SectionProxyMethodInvocation {
    private Object targetObejct;
    private ArrayList<SectionAdvice> advices;
    private SectionAspect targetSectionAspect;
    private Method method;
    private Object[] args;
    private int currentInterceptorIndex = -1;

    public ReflectiveSectionMethodInvocation(Object targetObejct,ArrayList<SectionAdvice> advices,SectionAspect targetSectionAspect,Method method,Object[] args){
        this.targetObejct = targetObejct;
        this.advices = advices;
        this.targetSectionAspect = targetSectionAspect;
        this.method = method;
        this.args = args;
    }

    @Override
    public Object proceed() throws Throwable {
        if(currentInterceptorIndex != advices.size()-1){
            SectionAdvice sectionAdvice = advices.get(++currentInterceptorIndex);
            return sectionAdvice.invoke(targetSectionAspect,this);  //必须有返回值,否则循环调用proceed几次就会执行几次invoke也就会调用几次目标方法几次
        }
        return invoke();
    }

    public Object invoke() throws Throwable {
        try {
            method.setAccessible(true);
           return method.invoke(targetObejct,args);
        }catch (IllegalArgumentException var5) {
            throw var5.getCause();
            //throw new AopInvocationException("AOP configuration seems to be invalid: tried calling method [" + method + "] on target [" + targetObejct + "]", var5);
        } catch (InvocationTargetException var4) {
            throw var4.getTargetException();
        }
    }
}
