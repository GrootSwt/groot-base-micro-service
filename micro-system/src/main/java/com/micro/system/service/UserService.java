package com.micro.system.service;


import com.groot.base.common.SearchData;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.bean.result.ResultPageDTO;
import com.micro.system.bean.ChangePasswordBean;
import com.micro.system.bean.LoginBean;
import com.micro.system.dto.UserDTO;
import com.micro.system.model.User;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;


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
    LoginBean login(User user, HttpServletResponse response);

    /**
     * 分页条件查询用户信息
     *
     * @param pageable   分页条件
     * @param searchData 查询条件
     * @return 用户列表
     */
    ResultPageDTO<UserDTO> pageableSearch(Pageable pageable, SearchData searchData);

    /**
     * 批量删除用户操作
     *
     * @param idArr 用户ids
     */
    void batchDelete(Long[] idArr);

    /**
     * 根据id获取用户
     *
     * @param toModel 用户
     */
    void addOrEditUser(User toModel);

    /**
     * 更改用户enabled
     *
     * @param toModel 用户id和用户enabled
     */
    void changeUserEnabled(User toModel);

    /**
     * 更改密码
     *
     * @param changePasswordBean 更改密码
     * @return 更改密码是否成功
     */
    void changePassword(ChangePasswordBean changePasswordBean);

    /**
     * 用户授权
     *
     * @param toModel 用户
     * @return 是否授权成功
     */
    ResultDTO<Void> authorization(User toModel);

    /**
     * 更改用户信息
     *
     * @param toModel 用户信息
     * @return 更改后的用户信息
     */
    User modifyUserInfo(User toModel);

    /**
     * 更改头像
     *
     * @param toModel 用户id、头像
     * @return 更改头像后的用户信息
     */
    User modifyAvatar(User toModel);

    void logout(String token);

    boolean loginNameIsExist(String loginName);

    boolean phoneNumberIsExist(String phoneNumber);

    boolean emailIsExist(String email);
}
