package com.yhlin.mapper;

import com.yhlin.bean.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order row);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int update(Order row);
}