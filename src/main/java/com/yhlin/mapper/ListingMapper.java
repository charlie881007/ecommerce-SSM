package com.yhlin.mapper;

import com.yhlin.bean.Listing;
import com.yhlin.enums.ListingStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ListingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Listing row);

    Listing selectByPrimaryKey(Integer id);

    List<Listing> selectAll();

    int update(Listing row);

    List<Listing> selectByStatus(@Param("status") ListingStatus status);
}