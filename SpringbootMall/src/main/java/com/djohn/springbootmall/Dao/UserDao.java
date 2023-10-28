package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Dto.UserRegisterRequest;
import com.djohn.springbootmall.Model.User;

public interface UserDao {


    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
