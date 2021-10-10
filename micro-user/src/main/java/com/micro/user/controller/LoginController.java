package com.micro.user.controller;

import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.ResultUtil;
import com.micro.user.convertor.UserConvertor;
import com.micro.user.service.UserService;
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
     * 登录账号密码校验，并获取用户角色、单位、部门、菜单权限和功能权限
     *
     * @param userDTO 登录用户DTO
     * @return 用户角色、单位、部门、菜单权限和功能权限
     */
    @ApiOperation(value = "登录")
    @PostMapping(value = "login")
    public ResultUtil login(@RequestBody UserDTO userDTO) {
        return userService.validateLoginInfoAndGenerateToken(userConvertor.toModel(userDTO));
    }

}
