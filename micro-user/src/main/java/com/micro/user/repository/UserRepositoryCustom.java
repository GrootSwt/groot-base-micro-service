package com.micro.user.repository;

import com.micro.common.util.SearchData;
import com.micro.user.model.User;
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

}
