package com.micro.system.controller;


import com.groot.base.common.OperatorInfo;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.util.LoginUserInfoUtil;
import com.micro.system.bean.LoginBean;
import com.micro.system.convertor.UserConvertor;
import com.micro.system.dto.UserDTO;
import com.micro.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    public ResultDTO<LoginBean> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        LoginBean bean = userService.login(userConvertor.toModel(userDTO), response);
        OperatorInfo info = new OperatorInfo();
        BeanUtils.copyProperties(userConvertor.toDTO(bean.getUserInfo()), info);
        LoginUserInfoUtil.setOperatorInfo(info);
        return ResultDTO.success("登录成功！", bean);
    }

    @ApiOperation(value = "登出")
    @GetMapping(value = "{token}/logout")
    public ResultDTO<Void> logout(@PathVariable String token) {
        userService.logout(token);
        return ResultDTO.success("已退出！");
    }

}
