package com.yhlin.mapper;

import com.yhlin.bean.Purchase;

import java.util.List;

public interface PurchaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Purchase row);

    Purchase selectByPrimaryKey(Integer id);

    List<Purchase> selectAll();

}