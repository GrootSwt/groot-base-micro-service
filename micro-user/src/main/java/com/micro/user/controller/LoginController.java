package com.micro.user.controller;

import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.ResultUtil;
import com.micro.user.convertor.UserConvertor;
import com.micro.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private UserConvertor userConvertor;

    @PostMapping(value = "login")
    public ResultUtil login(@RequestBody UserDTO userDTO) {
        return userService.validateLoginInfoAndGenerateToken(userConvertor.toModel(userDTO));
    }
}
