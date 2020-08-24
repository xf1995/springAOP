package com.example.demo.proxy;

import com.example.demo.aop.SectionAspect;
import com.example.demo.interfaces.SectionAdvice;
import com.example.demo.utils.ReflectiveSectionMethodInvocation;
import com.example.demo.utils.SpringContextHolder;
import com.example.demo.utils.TestBeanPostProcessor;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * @Author: XF
 * @Date: 2020/8/20 15:10
 * JDK动态代理
 */
public class JdkDynamicProxy implements InvocationHandler {

    private Object targetObejct;
    private Class<?> proxyInteface;
    private ArrayList<SectionAdvice> advices;
    private SectionAspect targetSectionAspect;

    public JdkDynamicProxy(Object obj,Class<?> proxyInteface,ArrayList<SectionAdvice> advices){
        this.targetObejct = obj;
        this.proxyInteface = proxyInteface;
        this.advices = advices;
    }

    public Object getProxy(){
        return getProxy(getDefaultClassLoader());
    }

    public Object getProxy(ClassLoader classLoader){
        return Proxy.newProxyInstance(classLoader,new Class<?>[]{proxyInteface},this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        try{
            Method m;
            if((m = macthMethod(method)) != null && TestBeanPostProcessor.getAnnotation(m.getAnnotations()) != null){
                targetSectionAspect = (SectionAspect) SpringContextHolder.getApplicationContext().getBean("sectionAspect");
                return new ReflectiveSectionMethodInvocation(targetObejct,advices,targetSectionAspect,method,args).proceed();
            }
            method.setAccessible(true);
            return method.invoke(targetObejct,args);
        } finally {
        }
    }

    public Method macthMethod(Method method){
        Class<?> clazz = targetObejct.getClass();
        for(Method m : clazz.getDeclaredMethods()){
            if(method.getName().equals(m.getName())){
                return m;
            }
        }
        return method;
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;

        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable var3) {
        }

        if (cl == null) {
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable var2) {
                }
            }
        }

        return cl;
    }
}
