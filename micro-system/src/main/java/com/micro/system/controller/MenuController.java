package com.micro.system.controller;


import com.groot.base.common.SearchData;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.bean.result.ResultListDTO;
import com.groot.base.web.bean.result.ResultPageDTO;
import com.groot.base.web.exception.BusinessRuntimeException;
import com.micro.system.convertor.MenuConvertor;
import com.micro.system.dto.MenuDTO;
import com.micro.system.model.Menu;
import com.micro.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
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
    public ResultListDTO<MenuDTO> getAllMenu() {
        List<MenuDTO> menuMap = menuService.getMapMenus(true);
        return ResultListDTO.success("获取全部菜单成功！", menuMap);
    }

    /**
     * 根据角色Id获取菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    @ApiOperation(value = "根据角色Id获取菜单列表")
    @GetMapping(value = "{roleId}/getMenuListByRoleId")
    public ResultListDTO<MenuDTO> getMenuListByRoleId(@PathVariable Long roleId) {
        List<MenuDTO> menuList = menuService.getMapMenusByRoleId(roleId);
        return ResultListDTO.success("根据角色id获取菜单列表成功！", menuList);
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
    public ResultPageDTO<MenuDTO> pageableMenu(SearchData searchData, Pageable pageable) {
        Page<Menu> menus = menuService.pageableMenu(searchData, pageable);
        return ResultPageDTO.success("分页条件查询菜单成功！", menuConvertor.toPageDTO(menus));
    }

    /**
     * 根据菜单Id获取菜单
     *
     * @param menuId 菜单Id
     * @return 菜单
     */
    @ApiOperation(value = "根据菜单Id获取菜单")
    @GetMapping(value = "{menuId}/getMenuByMenuId")
    public ResultDTO<MenuDTO> getMenuByMenuId(@PathVariable Long menuId) {
        Menu menu = this.menuService.getMenuByMenuId(menuId);
        return ResultDTO.success("根据菜单Id获取菜单成功！", menuConvertor.toDTO(menu));
    }

    /**
     * 保存修改或新增的菜单
     *
     * @param menuDTO 修改或新增的菜单
     * @return 保存菜单是否成功；如果成功，返回新增的菜单Tree
     */
    @ApiOperation(value = "保存修改或新增的菜单")
    @PostMapping(value = "saveMenu")
    public ResultListDTO<MenuDTO> saveMenu(@RequestBody MenuDTO menuDTO) {
        Menu menu = menuService.saveMenu(menuConvertor.toModel(menuDTO));
        if (menu != null) {
            List<MenuDTO> menuMap = menuService.getMapMenus(true);
            return ResultListDTO.success("保存菜单成功！", menuMap);
        }
        throw new BusinessRuntimeException("保存菜单失败！");
    }

    /**
     * 根据id列表删除菜单
     *
     * @param idArr id列表
     */
    @ApiOperation(value = "根据id列表删除菜单")
    @DeleteMapping(value = "deleteMenuByIdArr")
    public ResultListDTO<MenuDTO> deleteMenuByIdArr(Long[] idArr) {
        menuService.deleteMenuByIdArr(idArr);
        return ResultListDTO.success("删除菜单成功！", menuService.getMapMenus(true));
    }

    /**
     * 获取全部启用的菜单
     *
     * @return 全部启用菜单
     */
    @ApiOperation(value = "获取全部启用的菜单")
    @GetMapping(value = "getAllMenuForUser")
    public ResultListDTO<MenuDTO> getAllMenuForUser() {
        List<MenuDTO> menuList = menuService.getAllMenuForUser();
        return ResultListDTO.success("用户分配需要菜单获取成功！", menuList);
    }

    /**
     * 根据角色id获取权限
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    @ApiOperation(value = "根据角色id获取权限")
    @GetMapping(value = "{roleId}/getAuthorities")
    public ResultListDTO<String> getAuthorities(@PathVariable Long roleId) {
        List<String> authorities = menuService.getAuthorityByRoleId(roleId);
        return ResultListDTO.success("获取权限成功！", authorities);
    }

    /**
     * 判断将要新增的路径是否存在
     *
     * @param path 新增路径
     * @return 是否存在
     */
    @ApiOperation(value = "判断该路径是否已经存在")
    @GetMapping(value = "pathIsExist")
    public ResultDTO<Boolean> pathIsExist(@RequestParam String path) {
        boolean exist = menuService.pathIsExist(path);
        return ResultDTO.success("获取路径是否存在操作成功！", exist);
    }
}
