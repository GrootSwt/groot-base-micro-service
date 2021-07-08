package com.micro.user.service;

import com.micro.common.util.ResultUtil;
import com.micro.user.model.User;

public interface UserService {
    /**
     * 根据账号查询用户信息
     *
     * @param loginName 账号
     * @return 用户信息
     */
    User getUserByLoginName(String loginName);

    /**
     * 根据账号和密码查询用户信息
     *
     * @param loginName 账号
     * @param password  密码
     * @return 用户信息
     */
    User getUserByLoginNameAndPassword(String loginName, String password);

    /**
     * 校验登录人输入的账号密码，并获取用户、角色、菜单、权限的信息
     *
     * @param user 登录人输入的账号和密码
     * @return 登陆人的相关信息
     */
    ResultUtil validateLoginInfoAndGenerateToken(User user);
}
