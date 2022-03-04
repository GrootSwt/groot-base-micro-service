package com.groot.base.micro.system.controller;


import com.groot.base.common.SearchData;
import com.groot.base.web.bean.result.ResultDTO;
import com.groot.base.web.bean.result.ResultListDTO;
import com.groot.base.web.bean.result.ResultPageDTO;
import com.groot.base.micro.system.convertor.RoleConvertor;
import com.groot.base.micro.system.dto.RoleDTO;
import com.groot.base.micro.system.model.Role;
import com.groot.base.micro.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = {"角色"})
@RestController
@RequestMapping(value = "role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RoleConvertor roleConvertor;

    /**
     * 根据查询和分页条件获取角色列表
     *
     * @param searchData 查询条件
     * @param pageable   分页条件
     * @return 一页角色列表
     */
    @ApiOperation(value = "根据查询和分页条件获取角色列表")
    @GetMapping(value = "pageableSearch")
    public ResultPageDTO<RoleDTO> pageableSearch(SearchData searchData, Pageable pageable) {
        Page<Role> rolePage = roleService.pageableSearch(searchData, pageable);
        return ResultPageDTO.success("条件分页查询角色列表成功！", roleConvertor.toPageDTO(rolePage));
    }

    /**
     * 根据角色Id获取菜单Ids列表
     *
     * @param id 角色Id
     * @return 菜单列表Id
     */
    @ApiOperation(value = "根据角色Id获取菜单Ids列表")
    @GetMapping(value = "{id}/getMenuIdArrByRoleId")
    public ResultListDTO<String> getMenuIdArrByRoleId(@PathVariable String id) {
        List<String> menuIdList = roleService.getMenuIdArrByRoleId(id);
        return ResultListDTO.success("根据角色id获取菜单Id列表成功！", menuIdList);
    }

    /**
     * 角色分配权限
     *
     * @param roleId     角色Id
     * @param allMenuIds 菜单Id列表
     * @return 角色分配权限是否成功
     */
    @ApiOperation(value = "角色分配权限")
    @PutMapping(value = "{roleId}/assignPermissions")
    public ResultDTO<Void> assignPermissions(@PathVariable String roleId, @RequestBody List<String> allMenuIds) {
        roleService.assignPermissions(roleId, allMenuIds);
        return ResultDTO.success("角色分配权限成功！");
    }

    /**
     * 保存角色
     *
     * @param roleDTO 角色
     * @return 保存角色是否成功
     */
    @ApiOperation(value = "保存角色")
    @PostMapping(value = "saveRole")
    public ResultDTO<Void> saveRole(@RequestBody RoleDTO roleDTO) {
        roleService.saveRole(roleConvertor.toModel(roleDTO), roleDTO.getMenuIdArr());
        return ResultDTO.success("保存角色成功！");
    }

    /**
     * 批量删除角色成功
     *
     * @param ids 角色id列表
     * @return 批量删除成功
     */
    @ApiOperation(value = "批量删除角色成功")
    @DeleteMapping(value = "batchDeleteByIds")
    public ResultDTO<Void> batchDeleteByIds(String[] ids) {
        roleService.batchDeleteByIds(ids);
        return ResultDTO.success("批量删除角色成功！");
    }

    /**
     * 获取全部启用角色
     *
     * @return 全部启用角色
     */
    @ApiOperation(value = "获取全部启用角色")
    @GetMapping(value = "getAllRoleList")
    public ResultListDTO<RoleDTO> getAllRoleList() {
        List<Role> roleList = roleService.getAllRoleList();
        return ResultListDTO.success("获取所有角色成功！", roleConvertor.toListDTO(roleList));
    }

    /**
     * 更改角色状态
     *
     * @param roleDTO 角色id和角色enabled
     * @return 是否更改成功
     */
    @ApiOperation(value = "更改角色状态")
    @PutMapping(value = "changeRoleEnabled")
    public ResultDTO<Void> changeRoleEnabled(@RequestBody RoleDTO roleDTO) {
        roleService.changeRoleEnabled(roleConvertor.toModel(roleDTO));
        return ResultDTO.success("更改角色状态成功！");
    }

    /**
     * 获取新增角色名是否存在
     *
     * @param roleName 角色名
     * @return 角色名是否存在
     */
    @ApiOperation(value = "获取新增角色名是否存在")
    @GetMapping(value = "{roleName}/roleNameIsExist")
    public ResultDTO<Boolean> roleNameIsExist(@PathVariable String roleName) {
        return ResultDTO.success("获取新增角色名是否存在操作成功！", roleService.roleNameIsExist(roleName));
    }
}
