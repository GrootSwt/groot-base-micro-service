package com.micro.user.repository;

import com.micro.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * 根据账号查询用户信息
     *
     * @param loginName 账号
     * @return 用户信息
     */
    User findFirstByLoginNameAndEnabled(String loginName, String enabled);

    /**
     * 根据账号和密码查询用户信息
     *
     * @param loginName 账号
     * @param password  密码
     * @return 用户信息
     */
    User findFirstByLoginNameAndPassword(String loginName, String password);

    /**
     * 批量删除用户操作
     *
     * @param idArr 用户ids
     */
    void deleteByIdIn(Collection<Long> idArr);

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 用户信息
     */
    User findFirstById(Long id);

    User findFirstByRoleId(Long roleId);
}
