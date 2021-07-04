package com.micro.user.controller;

import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.JwtTokenUtil;
import com.micro.common.util.ResultUtil;
import com.micro.user.convertor.UserConvertor;
import com.micro.user.model.User;
import com.micro.user.repository.UserRepository;
import com.micro.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private UserConvertor userConvertor;

    @PostMapping(value = "login")
    public ResultUtil login(@RequestBody UserDTO userDTO) {
        return userService.validateLoginInfoAndGenerateToken(userConvertor.dtoToModel(userDTO));
    }

    @GetMapping(value = "test")
    public ResultUtil test() {
        return ResultUtil.success();
    }
}
