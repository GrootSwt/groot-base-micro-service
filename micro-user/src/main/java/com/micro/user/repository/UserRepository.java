package com.micro.user.repository;

import com.micro.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据账号查询用户信息
     *
     * @param loginName 账号
     * @return 用户信息
     */
    User findFirstByLoginName(String loginName);

    /**
     * 根据账号和密码查询用户信息
     *
     * @param loginName 账号
     * @param password  密码
     * @return 用户信息
     */
    User findFirstByLoginNameAndPassword(String loginName, String password);
}
