package com.yhlin.mapper;

import com.yhlin.bean.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag row);

    Tag selectByPrimaryKey(Integer id);

    List<Tag> selectAll();

}