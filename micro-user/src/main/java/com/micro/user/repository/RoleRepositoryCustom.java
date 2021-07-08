package com.micro.user.repository;

import com.micro.common.util.SearchData;
import com.micro.user.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepositoryCustom {
    /**
     * 根据查询和分页条件获取角色列表
     *
     * @param searchData 查询条件
     * @param pageable   分页条件
     * @return 一页角色列表
     */
    Page<Role> pageableSearch(SearchData searchData, Pageable pageable);

    /**
     * 批量删除角色
     *
     * @param ids 角色id列表
     */
    void batchDeleteByIds(Long[] ids);
}
