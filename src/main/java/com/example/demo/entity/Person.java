package com.example.demo.entity;

import javax.validation.constraints.NotBlank;

/**
 * @Author: XF
 * @Date: 2020/6/5 10:34
 */
public class Person {
    @NotBlank(message = "用户名不为空")
    private String username;
    private String id;
    public Person(String  username){
        this.username = username;
    }

    private Person(String id,String username){
        this.username = username;
        this.id = id;
    }

    public Person(){}
}
