package com.micro.system.user.repository;

import com.groot.base.common.SearchData;
import com.micro.system.user.bean.ChangePasswordBean;
import com.micro.system.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepositoryCustom {

    /**
     * 分页条件查询用户信息
     *
     * @param pageable   分页条件
     * @param searchData 查询条件
     * @return 用户列表
     */
    Page<User> pageableSearch(Pageable pageable, SearchData searchData);

    /**
     * 更改用户enabled
     *
     * @param toModel 用户id和用户enabled
     */
    void changeUserEnable(User toModel);

    /**
     * 更改密码
     * @param changePasswordBean    用户id和新密码
     */
    void changePassword(ChangePasswordBean changePasswordBean);
}
