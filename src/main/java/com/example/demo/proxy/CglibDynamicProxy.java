package com.example.demo.proxy;

import com.example.demo.aop.SectionAspect;
import com.example.demo.interfaces.SectionAdvice;
import com.example.demo.utils.ReflectiveSectionMethodInvocation;
import com.example.demo.utils.SpringContextHolder;
import com.example.demo.utils.TestBeanPostProcessor;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @Author: XF
 * @Date: 2020/8/20 15:10
 * Cglib动态代理
 */
public class CglibDynamicProxy {

    private Object targetObejct;  //目标类
    private ArrayList<SectionAdvice> advices; //切面方法类集合
    private SectionAspect targetSectionAspect; //切面类

    public CglibDynamicProxy(Object targetObejct,ArrayList<SectionAdvice> advices){
        this.targetObejct = targetObejct;
        this.advices = advices;
    }

    public Object getProxy(){
        return getProxy(null);
    }

    public Object getProxy(ClassLoader classLoader){
        Enhancer enhancer = this.createEnhancer();
        enhancer.setSuperclass(targetObejct.getClass());
        Callback[] callbacks = this.getCallbacks(targetObejct);
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        return createProxyClass(enhancer,callbacks);
    }

    //cglib代理方法拦截器处理拦截逻辑
    private static class DynamicAdviceInterceptor implements MethodInterceptor{
        private Object targetObejct;
        private SectionAspect targetSectionAspect; //切面类
        private ArrayList<SectionAdvice> advices; //切面方法类集合
        public DynamicAdviceInterceptor(Object targetObejct,SectionAspect targetSectionAspect,ArrayList<SectionAdvice> advices){
            this.targetObejct = targetObejct;
            this.targetSectionAspect = targetSectionAspect;
            this.advices = advices;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            try {
                Method m;
                if((m = macthMethod(method)) != null && TestBeanPostProcessor.getAnnotation(m.getAnnotations()) != null ){  //匹配当前当前方法在目标类中是否有指定注解
                    targetSectionAspect = (SectionAspect) SpringContextHolder.getApplicationContext().getBean("sectionAspect");
                    return new CglibMethodInvocation(targetObejct,advices,targetSectionAspect,method,objects,methodProxy).proceed();
                }
                method.setAccessible(true);
                return methodProxy.invoke(targetObejct,objects);
            }finally {
            }
        }

        private Method macthMethod(Method method){
            Class<?> clazz = targetObejct.getClass();
            for(Method m : clazz.getDeclaredMethods()){
                if(method.getName().equals(m.getName())){
                    return m;
                }
            }
            return method;
        }
    }

    public static class CglibMethodInvocation extends ReflectiveSectionMethodInvocation{
        private MethodProxy methodProxy;
        private boolean publicMethod;
        private Object targetObejct;
        private Object[] args;
        public CglibMethodInvocation(Object targetObejct, ArrayList<SectionAdvice> advices, SectionAspect targetSectionAspect, Method method, Object[] args,MethodProxy methodProxy) {
            super(targetObejct, advices, targetSectionAspect, method, args);
            this.methodProxy = methodProxy;
            this.targetObejct = targetObejct;
            this.args = args;
            publicMethod = Modifier.isPublic(method.getModifiers());
        }

        @Override
        public Object invoke() throws Throwable {
            return publicMethod?methodProxy.invoke(targetObejct,args):super.invoke();
        }
    }

    //cglib代理回调函数过滤器,指定方法拦截器
    private static class ProxyCallbackFilter implements CallbackFilter{

        @Override
        public int accept(Method method) {
            if("".equals(method.getName())){
                return 0;
            }
            return 0;
        }
    }

    private Object createProxyClass(Enhancer enhancer,Callback[] callbacks){
        enhancer.setCallbacks(callbacks);
        return enhancer.create();
    }

    //创建Enhancer
    private Enhancer createEnhancer(){
        return new Enhancer();
    }

    //回调函数
    private Callback[] getCallbacks(Object targetObejct){
        Callback sectionIntercepter = new DynamicAdviceInterceptor(targetObejct,targetSectionAspect,advices); //切面类
        return new Callback[]{sectionIntercepter};
    }

}
