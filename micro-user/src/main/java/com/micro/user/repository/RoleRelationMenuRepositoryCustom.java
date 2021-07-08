package com.micro.user.repository;

import java.util.List;

public interface RoleRelationMenuRepositoryCustom {

    /**
     * 根据角色Id获取菜单Ids列表
     *
     * @param roleId 角色Id
     * @return 菜单列表Id
     */
    List<Long> getMenuIdsByRoleId(Long roleId);
}
