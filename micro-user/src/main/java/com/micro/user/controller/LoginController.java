package com.micro.user.controller;

import com.micro.common.util.JwtTokenUtil;
import com.micro.common.util.ResultUtil;
import com.micro.common.model.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping(value = "login")
    public ResultUtil login(@RequestBody User user) {
        User userInfo = new User();
        userInfo.setLoginName(user.getLoginName());
        String token = JwtTokenUtil.generatorToken(userInfo, 60 * 60 * 2);
        Map<String, Object> data = new HashMap<>(16);
        data.put("token", token);
        data.put("loginName", user.getLoginName());
        return ResultUtil.success("登录成功！",data);
    }

    @GetMapping(value = "test")
    public ResultUtil test() {
        return ResultUtil.success();
    }
}
