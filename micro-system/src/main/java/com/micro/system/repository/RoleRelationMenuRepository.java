package com.micro.system.repository;


import com.micro.system.model.RoleRelationMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRelationMenuRepository extends JpaRepository<RoleRelationMenu, Long>, RoleRelationMenuRepositoryCustom {

    /**
     * 根据角色id查询出该角色和菜单关联列表
     *
     * @param roleId 角色Id
     * @return 角色、菜单关联列表
     */
    List<RoleRelationMenu> findAllByRoleId(Long roleId);

    /**
     * 根据角色id删除用户
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色id列表删除角色和菜单关联
     *
     * @param roleIds 角色id列表
     */
    void deleteByRoleIdIn(Collection<Long> roleIds);
}
