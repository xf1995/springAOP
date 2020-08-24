package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Author: XF
 * @Date: 2020/6/7 9:24
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object constraintViolationException(ConstraintViolationException con){
        Set<ConstraintViolation<?>> constraintViolations = con.getConstraintViolations();
        constraintViolations.forEach(c->System.out.println(c.getMessage()));
        return "参数错误!!!";
    }



}
