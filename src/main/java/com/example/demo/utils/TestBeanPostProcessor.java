package com.example.demo.utils;

import com.example.demo.anno.Front;
import com.example.demo.anno.Later;
import com.example.demo.anno.PointCut;
import com.example.demo.anno.Section;
import com.example.demo.interfaces.SectionAdvice;
import com.example.demo.proxy.CglibDynamicProxy;
import com.example.demo.proxy.JdkDynamicProxy;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: XF
 * @Date: 2020/6/23 9:16
 */
@Component
public class TestBeanPostProcessor extends AbstractAutoProxyCreator implements BeanPostProcessor{
    private ArrayList<SectionAdvice> advices = new ArrayList<>(16);
    private Map<String,Object> map = new ConcurrentHashMap<>(16);
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //System.out.println("当前beanName:"+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        //1.处理切面类
        Annotation annotation = clazz.getAnnotation(Section.class);
        if(annotation != null){ //说明当前bean是自定义切面
            Method [] methods = clazz.getMethods();
            for(Method method : methods){
                Annotation[] methodAnnotation = method.getAnnotations();
                Annotation anno = getAnnotation(methodAnnotation);
                SectionAdvice sectionAdvice = getAnnotation(anno,method);
                if(sectionAdvice != null){
                    advices.add(sectionAdvice);
                }
            }
        }
        //2.处理含有pointCut注解的bean方法
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            Annotation annotation1 = getAnnotation(method.getAnnotations());
            if(annotation1 != null && annotation1.annotationType().equals(PointCut.class)){
                Object obj;
                if(map.containsKey(beanName))  //缓存中有当前bean,不用进行代理直接返回当前bean
                    return bean;
                Class<?>[] superInterfaces = clazz.getInterfaces(); //获取父接口
                if(superInterfaces.length != 0) { //基于接口代理 jdk
                    map.put(beanName, obj = new JdkDynamicProxy(bean, superInterfaces[0], advices).getProxy());
                    return obj;
                }else{                           //基于类代理 cglib
                    map.put(beanName,obj = new CglibDynamicProxy(bean,advices).getProxy());
                    return obj;
                }
            }
        }
        return bean;
    }

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        Class<?> clazz = bean.getClass();
        for(Method method : clazz.getMethods()){
            Annotation annotation = getAnnotation(method.getAnnotations());
            if(annotation != null && annotation.annotationType().equals(PointCut.class)){
                Object obj;
                if(map.containsKey(beanName))
                    return map.get(beanName);
                Class<?>[] superInterfaces = clazz.getInterfaces(); //获取父接口
                if(superInterfaces.length != 0) { //基于接口代理 jdk
                    map.put(beanName, obj = new JdkDynamicProxy(bean, superInterfaces[0], advices).getProxy());
                    return obj;
                }else{                           //基于类代理 cglib
                    map.put(beanName,obj = new CglibDynamicProxy(bean,advices).getProxy());
                    return obj;
                }
            }
        }
        return super.wrapIfNecessary(bean, beanName, cacheKey);
    }

    public static SectionAdvice getAnnotation(Annotation annotation, Method method){
        if(annotation == null || method == null){
            return null;
        }
        if (Front.class.equals(annotation.annotationType())) {
            return new FrontAdvice(method);
        }else if(Later.class.equals(annotation.annotationType())){
            return new LaterAdvice(method);
        }
        return null;
    }

    public static Annotation getAnnotation(Annotation[] methodAnnotation){
        Class<?>[] clazzs = new Class[]{Front.class, Later.class, PointCut.class};
        for(Annotation annotation : methodAnnotation){
            for (Class c : clazzs){
                if(c.equals(annotation.annotationType())){
                    return annotation;
                }
            }
        }
        return null;
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, TargetSource targetSource) throws BeansException {
        return new Object[0];
    }
}
