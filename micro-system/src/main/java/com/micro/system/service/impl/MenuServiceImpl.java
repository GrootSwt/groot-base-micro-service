package com.micro.system.service.impl;


import com.groot.base.common.SearchData;
import com.groot.base.web.exception.BusinessRuntimeException;
import com.micro.system.convertor.MenuConvertor;
import com.micro.system.dto.MenuDTO;
import com.micro.system.model.Menu;
import com.micro.system.model.RoleRelationMenu;
import com.micro.system.repository.MenuRepository;
import com.micro.system.repository.RoleRelationMenuRepository;
import com.micro.system.service.MenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;
    @Resource
    private MenuConvertor menuConvertor;
    @Resource
    private RoleRelationMenuRepository roleRelationMenuRepository;

    @Override
    public List<MenuDTO> getMapMenus(boolean isAll) {
        List<Menu> allMenuList;
        if (isAll) {
            // 查询菜单和权限
            allMenuList = menuRepository.findAllByOrderBySort();
        } else {
            // 查询所有菜单
            allMenuList = menuRepository.findAllByTypeOrderBySort("1");
        }

        List<MenuDTO> menuDTOList = menuConvertor.toListDTO(allMenuList);
        MenuDTO menuDTO = new MenuDTO();
        this.menuListToMap(menuDTO, menuDTOList, 0L);
        return menuDTO.getChildren();
    }

    /**
     * 根据顶层父Id和菜单列表获取Map结构菜单
     *
     * @param menuDTO     Map结构菜单
     * @param menuDTOList 菜单列表
     * @param pId         父Id
     */
    private void menuListToMap(MenuDTO menuDTO, List<MenuDTO> menuDTOList, Long pId) {
        List<MenuDTO> menus = new ArrayList<>();
        menuDTOList.forEach(menu -> {
            if (menu.getParentId().equals(pId)) {
                this.menuListToMap(menu, menuDTOList, menu.getId());
                menus.add(menu);
            }
        });
        menuDTO.setChildren(menus);
    }

    public List<MenuDTO> getMapMenusByRoleId(Long roleId) {
        List<RoleRelationMenu> allByRoleId = roleRelationMenuRepository.findAllByRoleId(roleId);
        List<Long> roleIds = new ArrayList<>();
        allByRoleId.forEach(roleRelationMenu -> roleIds.add(roleRelationMenu.getMenuId()));
        List<Menu> menuList = menuRepository.findAllByIdInAndEnabledAndTypeOrderBySort(roleIds, "1", "1");
        List<MenuDTO> menuDTOList = menuConvertor.toListDTO(menuList);
        MenuDTO menuDTO = new MenuDTO();
        this.menuListToMap(menuDTO, menuDTOList, 0L);
        return menuDTO.getChildren();
    }

    @Override
    public Page<Menu> pageableMenu(SearchData searchData, Pageable pageable) {
        return menuRepository.pageableMenu(searchData, pageable);
    }

    @Override
    public Menu getMenuByMenuId(Long menuId) {
        return menuRepository.findFirstById(menuId);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        Long count = menuRepository.countAllByLocationAndType(menu.getLocation(), menu.getType());
        if (count > 0) {
            throw new BusinessRuntimeException("路径已存在，请修改后保存！");
        }
        // 重新整理排序
        List<Menu> menuList = menuRepository.findAllByParentIdOrderBySort(menu.getParentId());
        for (int i = 0; i < menuList.size(); i++) {
            if (i + 1 < Integer.parseInt(menu.getSort())) {
                menuList.get(i).setSort(String.valueOf(i + 1));
            } else {
                menuList.get(i).setSort(String.valueOf(i + 2));
            }
        }
        menuRepository.saveAll(menuList);
        return menuRepository.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuByIdArr(Long[] idArr) {
        menuRepository.deleteMenuByIdArr(idArr);

    }

    @Override
    public List<MenuDTO> getAllMenuForUser() {
        List<Menu> allMenuList = menuRepository.findAllByEnabledOrderBySort("1");
        List<MenuDTO> menuDTOList = menuConvertor.toListDTO(allMenuList);
        MenuDTO menuDTO = new MenuDTO();
        this.menuListToMap(menuDTO, menuDTOList, 0L);
        return menuDTO.getChildren();
    }

    @Override
    public List<String> getAuthorityByRoleId(Long roleId) {
        List<String> authorities = new ArrayList<>();
        List<RoleRelationMenu> roleRelationMenus = roleRelationMenuRepository.findAllByRoleId(roleId);
        List<Long> menuIds = new ArrayList<>();
        for (RoleRelationMenu roleRelationMenu : roleRelationMenus) {
            menuIds.add(roleRelationMenu.getMenuId());
        }
        List<Menu> menuList = menuRepository.findAllByIdInAndEnabledAndTypeOrderBySort(menuIds, "1", "2");
        for (Menu menu : menuList) {
            authorities.add(menu.getLocation());
        }
        return authorities;
    }

    @Override
    public boolean pathIsExist(String path) {
        Menu menu = menuRepository.findFirstByLocation(path);
        return menu != null;
    }
}
