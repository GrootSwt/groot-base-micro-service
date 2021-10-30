package com.micro.user.service.impl;

import com.micro.base.common.dto.user.MenuDTO;
import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.base.web.exception.BusinessRuntimeException;
import com.micro.user.convertor.MenuConvertor;
import com.micro.user.model.Menu;
import com.micro.user.model.RoleRelationMenu;
import com.micro.user.repository.MenuRepository;
import com.micro.user.repository.RoleRelationMenuRepository;
import com.micro.user.service.MenuService;
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
            allMenuList = menuRepository.findAllByOrderBySort();
        } else {
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
        if (roleId.equals(1L)) {
            return this.getMapMenus(false);
        }
        List<RoleRelationMenu> allByRoleId = roleRelationMenuRepository.findAllByRoleId(roleId);
        List<Long> roleIds = new ArrayList<>();
        allByRoleId.forEach(roleRelationMenu -> {
            roleIds.add(roleRelationMenu.getMenuId());
        });
        List<Menu> menuList = menuRepository.findAllByIdInAndEnabledAndTypeOrderBySort(roleIds, "1", "1");
        List<MenuDTO> menuDTOList = menuConvertor.toListDTO(menuList);
        MenuDTO menuDTO = new MenuDTO();
        this.menuListToMap(menuDTO, menuDTOList, 0L);
        return menuDTO.getChildren();
    }

    @Override
    public ResultData pageableMenu(SearchData searchData, Pageable pageable) {
        Page<Menu> menuList = menuRepository.pageableMenu(searchData, pageable);
        Page<MenuDTO> menuDTOPage = menuConvertor.toPageDTO(menuList);
        return ResultData.success("分页条件查询菜单列表成功！", menuDTOPage);
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
        if (roleId == 1L) {
            List<Menu> menuList = menuRepository.findAllByTypeOrderBySort("2");
            for (Menu menu : menuList) {
                authorities.add(menu.getLocation());
            }
        } else {
            List<RoleRelationMenu> roleRelationMenus = roleRelationMenuRepository.findAllByRoleId(roleId);
            List<Long> menuIds = new ArrayList<>();
            for (RoleRelationMenu roleRelationMenu : roleRelationMenus) {
                menuIds.add(roleRelationMenu.getMenuId());
            }
            List<Menu> menuList = menuRepository.findAllByIdInAndEnabledAndTypeOrderBySort(menuIds, "1", "2");
            for (Menu menu : menuList) {
                authorities.add(menu.getLocation());
            }
        }
        return authorities;
    }
}
