package com.example.demo.entity;

import com.example.demo.anno.PointCut;
import com.example.demo.anno.RetryAnnotation;
import com.example.demo.interfaces.InterfaceY;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: XF
 * @Date: 2020/5/24 10:12
 */
//@Service
@Slf4j
public class Y implements InterfaceY{

    @Autowired
    X x;

    public Y(){
        System.out.println("Y class 创建");
    }

    @Override
    @PointCut
    public String interfaceY() {
        log.info("y.class interfaceY");
        return "interfaceY";
    }

    @Override
    public String noInterfaceY() {
        log.info("noInterfaceY");
        return "noInterfaceY";
    }
}
