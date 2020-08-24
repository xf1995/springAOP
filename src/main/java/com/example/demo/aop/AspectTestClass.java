package com.example.demo.aop;

import com.example.demo.anno.Authentication;
import com.example.demo.anno.RetryAnnotation;
import com.example.demo.exception.UpdateException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: XF
 * @Date: 2020/7/12 10:28
 */
//@Aspect
//@Component
@Slf4j
public class AspectTestClass {

    @Pointcut("@annotation(retryAnnotation)")
    public void pointCut(RetryAnnotation retryAnnotation){
    }

    //@Around("pointCut(retryAnnotation)")
    public Object around(ProceedingJoinPoint pjp,RetryAnnotation retryAnnotation) throws Throwable {
        int tryMaxNum = retryAnnotation.retry();
        int teyNum = 0;

        for(;;){
            teyNum++;
            try{
                Object result = pjp.proceed();
                return result;
            }catch (UpdateException e){
                System.out.println("操作异常!");
                if(teyNum >= tryMaxNum){
                    return "错误";
                }
            }
        }
    }

    @Before("pointCut(retryAnnotation)")
    public void before(RetryAnnotation retryAnnotation){
        log.info("执行了增强操作+before");
    }
    @After("pointCut(retryAnnotation)")
    public void after(RetryAnnotation retryAnnotation){
        log.info("执行了增强操作+after");
    }
}
