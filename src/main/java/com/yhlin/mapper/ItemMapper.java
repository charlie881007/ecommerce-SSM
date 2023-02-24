package com.yhlin.mapper;

import com.yhlin.bean.Item;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item row);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll();

}