package com.djohn.springbootmall.Service;

import com.djohn.springbootmall.Dto.UserRegisterRequest;
import com.djohn.springbootmall.Model.User;

public interface UserService {


    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
}
