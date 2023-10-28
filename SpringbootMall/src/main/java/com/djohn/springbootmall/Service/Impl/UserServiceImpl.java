package com.djohn.springbootmall.Service.Impl;


import com.djohn.springbootmall.Dao.UserDao;
import com.djohn.springbootmall.Dto.UserRegisterRequest;
import com.djohn.springbootmall.Model.User;
import com.djohn.springbootmall.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {


    private  final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;


    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //檢查註冊的email
        //要在Service層去檢查 User 是否有重複用同一個Email去註冊帳號
        //所以我們拿一個email丟過去後端，看是不是能拿回一個 user 類型的 Object
       User user =  userDao.getUserByEmail(userRegisterRequest.getEmail());

       if(user != null){
           log.warn("該 email {} 已經被註冊",userRegisterRequest.getEmail());
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }

        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }
}
