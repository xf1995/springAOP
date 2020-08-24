package com.example.demo.contrlloer;

import com.example.demo.anno.RetryAnnotation;
import com.example.demo.entity.Price;
import com.example.demo.exception.UpdateException;
import com.example.demo.mapper.Mapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @Author: XF
 * @Date: 2020/7/22 10:25
 */
//@Controller
public class TestController {

    //@Autowired
    Mapper mapper;

    @RequestMapping("/upData")
    @ResponseBody
    @RetryAnnotation
    public Object upData(){
        Price price = mapper.selectPriceByPrimaryKey("2");
        System.out.println(Thread.currentThread().getName()+"    "+price.getPrice());
        int count = mapper.updatePriceByPrimarykey(new BigDecimal(1).add(price.getPrice()),price.getId(),price.getVersion());
        if (count != 0){
            System.out.println("更新成功");
        }else {
            System.out.println("更新失败");
            throw new UpdateException();
        }
        /*ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i =0;i<5;i++){
            Thread thread = new Thread(()-> {
                Price price = mapper.selectPriceByPrimaryKey("2");
                System.out.println(Thread.currentThread().getName()+"    "+price.getPrice());
                int count = mapper.updatePriceByPrimarykey( new BigDecimal(200).add(price.getPrice()),price.getId(),price.getVersion());
                if (count == 0){
                    System.out.println("更新失败");
                }else {
                    System.out.println("更新成功");
                }
            });
            executorService.submit(thread);
        }*/
        return "123";
    }

}
