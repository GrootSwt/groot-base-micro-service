package com.micro.system.user.service;


import com.groot.base.common.SearchData;
import com.micro.system.user.dto.MenuDTO;
import com.micro.system.user.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {

    /**
     * 获取全部Map结构菜单
     *
     * @return Map结构菜单
     */
    List<MenuDTO> getMapMenus(boolean isALl);

    /**
     * 根据角色Id查询Map结构菜单
     *
     * @param roleId 角色Id
     * @return Map结构菜单列表
     */
    List<MenuDTO> getMapMenusByRoleId(Long roleId);

    /**
     * 条件分页查询菜单
     *
     * @param searchData 条件
     * @param pageable   分页条件
     * @return 分页菜单列表
     */
    Page<Menu> pageableMenu(SearchData searchData, Pageable pageable);

    /**
     * 根据菜单Id获取菜单
     *
     * @param menuId 菜单Id
     * @return 菜单
     */
    Menu getMenuByMenuId(Long menuId);

    /**
     * 保存菜单
     *
     * @param menu 需要修改或新增的菜单
     * @return 保存之后的菜单
     */
    Menu saveMenu(Menu menu);

    /**
     * 根据id列表删除菜单
     *
     * @param idArr id列表
     */
    void deleteMenuByIdArr(Long[] idArr);

    /**
     * 获取全部启用的菜单
     *
     * @return 全部启用菜单
     */
    List<MenuDTO> getAllMenuForUser();

    /**
     * 根据角色id获取操作权限列表
     *
     * @param roleId 角色id
     * @return 操作权限列表
     */
    List<String> getAuthorityByRoleId(Long roleId);

    /**
     * 判断将要新增的路径是否存在
     *
     * @param path 新增路径
     * @return 是否存在
     */
    boolean pathIsExist(String path);
}
