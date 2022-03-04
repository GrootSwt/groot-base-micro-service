package com.groot.base.micro.system.repository;


import com.groot.base.micro.system.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String>, RoleRepositoryCustom {
    /**
     * 根据Id查询角色
     *
     * @param roleId 角色Id
     * @return 角色
     */
    Role findFirstById(String roleId);

    /**
     * 获取第一个角色名相同的角色
     *
     * @param roleName 角色名
     * @return 角色
     */
    Role findFirstByName(String roleName);
}
