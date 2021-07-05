package com.micro.user.service.impl;

import com.micro.user.model.Menu;
import com.micro.user.model.RoleRelationMenu;
import com.micro.user.repository.MenuRepository;
import com.micro.user.repository.RoleRelationMenuRepository;
import com.micro.user.repository.RoleRepository;
import com.micro.user.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RoleRelationMenuRepository roleRelationMenuRepository;

    @Override
    public List<Menu> getMapMenus() {
        List<Menu> allMenuList = menuRepository.findAllByOrderBySort();
        Menu menuMap = new Menu();
        this.menuListToMap(menuMap, allMenuList, 0L);
        return menuMap.getChildren();
    }

    private void menuListToMap(Menu menuMap, List<Menu> allMenuList, Long pId) {
        List<Menu> menus = new ArrayList<>();
        allMenuList.forEach(menu -> {
            if (menu.getPId().equals(pId)) {
                this.menuListToMap(menu, allMenuList, menu.getId());
                menus.add(menu);
            }
        });
        menuMap.setChildren(menus);
    }

    public List<Menu> getMapMenusByRoleId(Long roleId) {
        List<RoleRelationMenu> allByRoleId = roleRelationMenuRepository.findAllByRoleId(roleId);
        List<Long> roleIds = new ArrayList<>();
        allByRoleId.forEach(roleRelationMenu -> {
            roleIds.add(roleRelationMenu.getMenuId());
        });
        List<Menu> menuList = menuRepository.findAllByIdInOrderBySort(roleIds);
        Menu menuMap = new Menu();
        this.menuListToMap(menuMap, menuList, 0L);
        return menuMap.getChildren();
    }
}
