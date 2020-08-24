package com.example.demo.entity;

import com.example.demo.anno.PointCut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: XF
 * @Date: 2020/8/23 14:38
 */
@Service
@Slf4j
public class T {

    @PointCut
    public String returnString(){
        log.info("T.class returnString");
        return "T.class returnString";
    }

    public String noReturnString(){
        log.info("T.class returnString");
        return "T.class returnString";
    }
}
