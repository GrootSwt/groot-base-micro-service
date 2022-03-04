package com.groot.base.micro.system.repository;

import java.util.List;

public interface RoleRelationMenuRepositoryCustom {

    /**
     * 根据角色Id获取菜单Ids列表
     *
     * @param roleId 角色Id
     * @return 菜单列表Id
     */
    List<String> getMenuIdsByRoleId(String roleId);
}
