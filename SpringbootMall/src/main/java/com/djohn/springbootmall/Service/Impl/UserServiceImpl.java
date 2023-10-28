package com.djohn.springbootmall.Service.Impl;


import com.djohn.springbootmall.Dao.UserDao;
import com.djohn.springbootmall.Dto.UserRegisterRequest;
import com.djohn.springbootmall.Model.User;
import com.djohn.springbootmall.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;


    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
