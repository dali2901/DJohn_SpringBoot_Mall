package com.djohn.springbootmall.Controller;

import com.djohn.springbootmall.Dto.UserRegisterRequest;
import com.djohn.springbootmall.Model.User;
import com.djohn.springbootmall.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){


        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
        //這邊返回一個User類型的 user物件給前端時 ， 密碼會跟著一起user物件一起傳送過去造成資料洩漏
        //所以要在User類型的 Model 去做一個 @JsonIgnore註解


    }
}
