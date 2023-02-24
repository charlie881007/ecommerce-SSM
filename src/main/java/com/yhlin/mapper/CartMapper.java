package com.yhlin.mapper;

import com.yhlin.bean.Cart;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart row);

    Cart selectByPrimaryKey(Integer id);


    List<Cart> selectAll();

    int update(Cart row);
}