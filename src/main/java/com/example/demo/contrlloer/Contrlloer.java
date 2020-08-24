package com.example.demo.contrlloer;

import com.example.demo.entity.T;
import com.example.demo.entity.X;
import com.example.demo.interfaces.InterfaceY;
import com.example.demo.utils.RingBufferWheel;
import com.example.demo.entity.Y;
import com.example.demo.utils.SpringContextHolder;
import format.FormatTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: XF
 * @Date: 2020/5/26 9:44
 */
@Controller
@Slf4j
//@Validated
public class Contrlloer {
    /*public Contrlloer(){
        System.out.println("Class Contrlloer 创建");
    }*/

    Logger logger = LoggerFactory.getLogger(Contrlloer.class);
    @Autowired
    InterfaceY y;

    @Autowired
    X x;

    @Autowired
    T t;

    @Autowired
    FormatTemplate formatTemplate;

    /*@Autowired
    RingBufferWheel ringBufferWheel;*/


    @RequestMapping("/test")
    @ResponseBody
    public String test(/*@NotBlank(message = "id不能为空!") String id*/){
        //String s = formatTemplate.doFormat();
        //System.out.println(s);
        /*Object ob = x.test();
        log.info(ob+"");
        Object obj = y.noInterfaceY();
        log.info(obj+"");*/
        String s = t.noReturnString();
        log.info(s);
        return "1111";
    }




    private class Job extends RingBufferWheel.Task{

        public  Job(int num){
            this.key = num;
        }
        @Override
        public void run() {
           String str = formatTemplate.doFormat();
           System.out.println(str);
        }
    }

}
