package com.yhlin.mapper;

import com.yhlin.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User row);

    User selectByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    User selectByEmail(String email);


    List<User> selectAll();

    int update(User user);
}