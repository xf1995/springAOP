package com.example.demo.entity;

import lombok.*;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: XF
 * @Date: 2020/7/22 10:52
 */
@Data
@Log
public class Price implements Serializable {
    private String id;

    private BigDecimal price;

    private int version;
   /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }*/
}
