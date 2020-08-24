package com.example.demo.entity;

import com.example.demo.anno.PointCut;
import com.example.demo.entity.Person;
import com.example.demo.anno.EnableExport;
import com.example.demo.anno.EnableExportField;
import com.example.demo.interfaces.InterfaceY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XF
 * @Date: 2020/5/24 10:12
 */
//@Service
public class X{

    @Autowired
    InterfaceY y;

    public X(){
        System.out.println("X class 创建");
    }

    public String self(){
        return "y.self()";
    }

    //@Async
    public String test(){
        String test = y.interfaceY();
        System.out.println(test);
        return test;
    }

    public void export() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Person> personList = new ArrayList<>();

        Class clazz = Person.class;
        Class superClazz = clazz.getSuperclass();//获取当前class的父类

        if(clazz.isAnnotationPresent(EnableExport.class)){
            EnableExport enableExport = (EnableExport) clazz.getAnnotation(EnableExport.class);
            String fileName = enableExport.fileName(); //获取文件名
            List<Field> fieldList = new ArrayList<>();
            List<String> colNameList = new ArrayList<>();

            for(Field field : superClazz.getDeclaredFields()){ //获取父类所有字段,循环
                if(field.isAnnotationPresent(EnableExportField.class)){
                    EnableExportField enableExportField = field.getAnnotation(EnableExportField.class);
                    fieldList.add(field);
                    colNameList.add(enableExportField.colName());
                }
            }
            for(Field field : clazz.getDeclaredFields()){ //获取本类的所有字段
                if(field.isAnnotationPresent(EnableExportField.class)){
                    EnableExportField enableExportField = field.getAnnotation(EnableExportField.class);
                    fieldList.add(field);
                    colNameList.add(enableExportField.colName());
                }
            }
            for(Person person : personList){
                for(int j=0;j<fieldList.size();j++){
                    Object value;
                    Field field = fieldList.get(j);
                    field.setAccessible(true);
                    value = field.get(person);
                    EnableExportField enableExportField = field.getAnnotation(EnableExportField.class);
                    if(!"".equals(enableExportField.getMethod())){
                        Method method = clazz.getMethod(enableExportField.getMethod(),new Class[]{field.getType()});
                        value = method.invoke(person,value);
                    }
                    if(!"".equals(enableExportField.readConverterExp())){

                    }

                }
            }
        }
    }
}
