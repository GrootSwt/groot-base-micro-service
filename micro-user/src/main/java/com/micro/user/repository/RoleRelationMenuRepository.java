package com.micro.user.repository;

import com.micro.user.model.RoleRelationMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRelationMenuRepository extends JpaRepository<RoleRelationMenu, Long> {

    List<RoleRelationMenu> findAllByRoleId(Long roleId);
}
