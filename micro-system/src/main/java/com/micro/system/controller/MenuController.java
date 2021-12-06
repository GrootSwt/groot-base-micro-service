package com.micro.system.controller;

import com.micro.base.common.dto.system.MenuDTO;
import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.system.convertor.MenuConvertor;
import com.micro.system.model.Menu;
import com.micro.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 */
@Api(tags = {"菜单"})
@RestController
@RequestMapping(value = "menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @Resource
    private MenuConvertor menuConvertor;

    /**
     * 获取全部Tree菜单列表
     *
     * @return Tree菜单列表
     */
    @ApiOperation(value = "获取全部Tree菜单列表")
    @GetMapping(value = "getAllMenu")
    public ResultData getAllMenu() {
        List<MenuDTO> menuMap = menuService.getMapMenus(true);
        return ResultData.success("获取全部菜单成功！", menuMap);
    }

    /**
     * 根据角色Id获取菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    @ApiOperation(value = "根据角色Id获取菜单列表")
    @GetMapping(value = "{roleId}/getMenuListByRoleId")
    public ResultData getMenuListByRoleId(@PathVariable Long roleId) {
        List<MenuDTO> menuList = menuService.getMapMenusByRoleId(roleId);
        return ResultData.success("根据角色id获取菜单列表成功！", menuList);
    }

    /**
     * 分页条件查询菜单
     *
     * @param searchData 查询条件
     * @param pageable   分页信息
     * @return 一页符合条件的菜单列表
     */
    @ApiOperation(value = "分页条件查询菜单")
    @GetMapping(value = "pageableMenu")
    public ResultData pageableMenu(SearchData searchData, Pageable pageable) {
        return menuService.pageableMenu(searchData, pageable);
    }

    /**
     * 根据菜单Id获取菜单
     *
     * @param menuId 菜单Id
     * @return 菜单
     */
    @ApiOperation(value = "根据菜单Id获取菜单")
    @GetMapping(value = "{menuId}/getMenuByMenuId")
    public ResultData getMenuByMenuId(@PathVariable Long menuId) {
        Menu menu = this.menuService.getMenuByMenuId(menuId);
        return ResultData.success("根据菜单Id获取菜单成功！", menuConvertor.toDTO(menu));
    }

    /**
     * 保存修改或新增的菜单
     *
     * @param menuDTO 修改或新增的菜单
     * @return 保存菜单是否成功；如果成功，返回新增的菜单Tree
     */
    @ApiOperation(value = "保存修改或新增的菜单")
    @PostMapping(value = "saveMenu")
    public ResultData saveMenu(@RequestBody MenuDTO menuDTO) {
        Menu menu = menuService.saveMenu(menuConvertor.toModel(menuDTO));
        if (menu != null) {
            List<MenuDTO> menuMap = menuService.getMapMenus(true);
            return ResultData.success("保存菜单成功！", menuMap);
        }
        return ResultData.failure("保存菜单失败！");
    }

    /**
     * 根据id列表删除菜单
     *
     * @param idArr id列表
     */
    @ApiOperation(value = "根据id列表删除菜单")
    @DeleteMapping(value = "deleteMenuByIdArr")
    public ResultData deleteMenuByIdArr(Long[] idArr) {
        menuService.deleteMenuByIdArr(idArr);
        return ResultData.success("删除菜单成功！", menuService.getMapMenus(true));
    }

    /**
     * 获取全部启用的菜单
     *
     * @return 全部启用菜单
     */
    @ApiOperation(value = "获取全部启用的菜单")
    @GetMapping(value = "getAllMenuForUser")
    public ResultData getAllMenuForUser() {
        List<MenuDTO> menuList = menuService.getAllMenuForUser();
        return ResultData.success("用户分配需要菜单获取成功！", menuList);
    }

    /**
     * 根据角色id获取权限
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    @ApiOperation(value = "根据角色id获取权限")
    @GetMapping(value = "{roleId}/getAuthorities")
    public ResultData getAuthorities(@PathVariable Long roleId) {
        List<String> authorities = menuService.getAuthorityByRoleId(roleId);
        return ResultData.success("获取权限成功！", authorities);
    }
}
