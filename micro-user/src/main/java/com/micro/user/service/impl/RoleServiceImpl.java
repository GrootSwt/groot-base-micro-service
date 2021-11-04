package com.micro.user.service.impl;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.user.model.Role;
import com.micro.user.model.RoleRelationMenu;
import com.micro.user.model.User;
import com.micro.user.repository.RoleRelationMenuRepository;
import com.micro.user.repository.RoleRepository;
import com.micro.user.repository.UserRepository;
import com.micro.user.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RoleRelationMenuRepository roleRelationMenuRepository;
    @Resource
    private UserRepository userRepository;

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
    public ResultData saveRole(Role role, Long[] menuIdArr) {
        if (role.getId() != null && "0".equals(role.getEnabled())) {
            User user = userRepository.findFirstByRoleId(role.getId());
            if (user != null) {
                return ResultData.failure("该角色下存在用户，不可禁用！");
            }
        }
        // 编辑时先删除角色关联菜单
        if (role.getId() != null) {
            roleRelationMenuRepository.deleteByRoleId(role.getId());
        }
        // 保存角色
        Role result = roleRepository.save(role);
        // 如果菜单id列表不为空，将数据插入角色菜单关联表
        if (menuIdArr.length > 0) {
            List<RoleRelationMenu> roleRelationMenus = new ArrayList<>();
            for (Long menuId : menuIdArr) {
                RoleRelationMenu roleRelationMenu = new RoleRelationMenu();
                roleRelationMenu.setRoleId(result.getId());
                roleRelationMenu.setMenuId(menuId);
                roleRelationMenus.add(roleRelationMenu);
            }
            roleRelationMenuRepository.saveAll(roleRelationMenus);
        }
        return ResultData.success("保存成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData batchDeleteByIds(Long[] ids) {
        for (Long id : ids) {
            User user = userRepository.findFirstByRoleId(id);
            if (user != null) {
                return ResultData.failure("批量删除的角色列表中有用户,不可删除！");
            }
        }
        roleRepository.batchDeleteByIds(ids);
        roleRelationMenuRepository.deleteByRoleIdIn(Arrays.asList(ids));
        return ResultData.success("角色批量删除成功！");
    }

    @Override
    public List<Role> getAllRoleList() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData changeRoleEnabled(Role toModel) {
        if ("0".equals(toModel.getEnabled())) {
            User user = userRepository.findFirstByRoleId(toModel.getId());
            if (user != null) {
                return ResultData.failure("该角色下有用户，不可禁用！");
            }
        }
        roleRepository.changeRoleEnabled(toModel);
        return ResultData.success("角色状态改变操作成功！");
    }
}
