package com.micro.user.repository;

import com.micro.user.model.RoleRelationMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRelationMenuRepository extends JpaRepository<RoleRelationMenu, Long>,RoleRelationMenuRepositoryCustom {

    /**
     * 根据角色id查询出该角色和菜单关联列表
     *
     * @param roleId 角色Id
     * @return 角色、菜单关联列表
     */
    List<RoleRelationMenu> findAllByRoleId(Long roleId);

    Long deleteByRoleId(Long roleId);
}
