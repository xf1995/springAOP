package com.example.demo.mapper;

import com.example.demo.entity.Price;

import java.math.BigDecimal;

/**
 * @Author: XF
 * @Date: 2020/5/28 10:04
 */
public interface Mapper {
    int deleteByPrimaryKey(Integer id);

    Price selectPriceByPrimaryKey(String id);

    int updatePriceByPrimarykey(BigDecimal price,String id,Integer version);
}
