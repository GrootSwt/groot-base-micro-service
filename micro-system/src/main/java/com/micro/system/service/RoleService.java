package com.micro.system.service;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.system.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    /**
     * 根据角色Id查询角色
     *
     * @param roleId 角色Id
     * @return 角色
     */
    Role findFirstById(Long roleId);

    /**
     * 根据查询和分页条件获取角色列表
     *
     * @param searchData 查询条件
     * @param pageable   分页条件
     * @return 一页角色列表
     */
    Page<Role> pageableSearch(SearchData searchData, Pageable pageable);

    /**
     * 根据角色Id获取菜单Ids列表
     *
     * @param id 角色Id
     * @return 菜单列表Id
     */
    List<Long> getMenuIdArrByRoleId(Long id);

    /**
     * 角色分配权限
     *
     * @param roleId     角色Id
     * @param allMenuIds 菜单Id列表
     */
    void assignPermissions(Long roleId, List<Long> allMenuIds);

    /**
     * 保存角色
     *
     * @param toModel 角色
     */
    ResultData saveRole(Role toModel, Long[] menuIdArr);

    /**
     * 批量删除角色
     *
     * @param ids 角色id列表
     */
    ResultData batchDeleteByIds(Long[] ids);

    /**
     * 获取全部启用角色
     *
     * @return 全部启用角色
     */
    List<Role> getAllRoleList();

    /**
     * 更改角色状态
     *
     * @param toModel 角色id和角色enabled
     */
    ResultData changeRoleEnabled(Role toModel);
}
