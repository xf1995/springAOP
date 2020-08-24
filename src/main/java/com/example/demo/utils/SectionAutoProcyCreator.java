package com.example.demo.utils;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

/**
 * @Author: XF
 * @Date: 2020/8/21 16:39
 */
public class SectionAutoProcyCreator extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, TargetSource targetSource) throws BeansException {
        return new Object[0];
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
        return super.getEarlyBeanReference(bean, beanName);
    }

    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {


        return super.wrapIfNecessary(bean, beanName, cacheKey);
    }
}
