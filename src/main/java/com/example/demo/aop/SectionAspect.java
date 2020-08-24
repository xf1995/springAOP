package com.example.demo.aop;

import com.example.demo.anno.Front;
import com.example.demo.anno.Later;
import com.example.demo.anno.Section;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: XF
 * @Date: 2020/8/20 9:58
 * 自定义切面
 */
@Section
@Component
@Slf4j
public class SectionAspect {

    @Front
    public void before(){
        log.info("调用了自定义切面Section的前置方法!");
    }

    @Later
    public void after(){
        log.info("调用了自定义切面Section的后置方法!");
    }
}
