package com.micro.user.service;

import com.micro.common.util.ResultUtil;
import com.micro.common.util.SearchData;
import com.micro.user.model.User;
import org.springframework.data.domain.Pageable;


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

    /**
     * 分页条件查询用户信息
     *
     * @param pageable   分页条件
     * @param searchData 查询条件
     * @return 用户列表
     */
    ResultUtil pageableSearch(Pageable pageable, SearchData searchData);

    /**
     * 批量删除用户操作
     *
     * @param idArr 用户ids
     */
    void batchDelete(Long[] idArr);

    /**
     * 根据id获取用户
     * @param toModel   用户
     */
    void addOrEditUser(User toModel);
}
