package com.micro.user.repository;

import com.micro.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {
    /**
     * 根据Id查询角色
     *
     * @param roleId 角色Id
     * @return 角色
     */
    Role findFirstById(Long roleId);

    /**
     * 根据enable查询
     *
     * @param enabled 启用状态
     * @return 符合启用条件的角色列表
     */
    List<Role> findAllByEnabled(String enabled);

}
