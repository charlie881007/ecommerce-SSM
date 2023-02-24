package com.yhlin.mapper;

import com.yhlin.bean.CartDetail;

public interface CartDetailMapper {
    int insert(CartDetail cartDetail);

    int updateQuantity(CartDetail cartDetail);

    int delete(CartDetail cartDetail);

}
