package com.micro.user.service.impl;

import com.micro.common.util.SearchData;
import com.micro.user.model.Role;
import com.micro.user.model.RoleRelationMenu;
import com.micro.user.repository.RoleRelationMenuRepository;
import com.micro.user.repository.RoleRepository;
import com.micro.user.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RoleRelationMenuRepository roleRelationMenuRepository;
    @Override
    public Role findFirstById(Long roleId) {
        return roleRepository.findFirstById(roleId);
    }

    @Override
    public Page<Role> pageableSearch(SearchData searchData, Pageable pageable) {
        return roleRepository.pageableSearch(searchData, pageable);
    }

    @Override
    public List<Long> getMenuIdArrByRoleId(Long id) {
        return roleRelationMenuRepository.getMenuIdsByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissions(Long roleId, List<Long> allMenuIds) {
        roleRelationMenuRepository.deleteByRoleId(roleId);
        allMenuIds.forEach(menuId -> {
            RoleRelationMenu roleRelationMenu = new RoleRelationMenu();
            roleRelationMenu.setRoleId(roleId);
            roleRelationMenu.setMenuId(menuId);
            roleRelationMenuRepository.save(roleRelationMenu);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteByIds(Long[] ids) {
        roleRepository.batchDeleteByIds(ids);
    }

    @Override
    public List<Role> getAllRoleList() {
        return roleRepository.findAll();
    }
}
