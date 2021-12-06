package com.micro.system.controller;

import com.micro.base.common.dto.system.UserDTO;
import com.micro.base.common.bean.ResultData;
import com.micro.system.convertor.UserConvertor;
import com.micro.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录功能
 */
@Api(tags = {"登录"})
@RestController
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private UserConvertor userConvertor;

    /**
     * 登录账号密码校验，并获取用户信息、token
     *
     * @param userDTO 登录用户DTO
     * @return 用户信息、token
     */
    @ApiOperation(value = "登录")
    @PostMapping(value = "login")
    public ResultData login(@RequestBody UserDTO userDTO) {
        return userService.validateLoginInfoAndGenerateToken(userConvertor.toModel(userDTO));
    }

}