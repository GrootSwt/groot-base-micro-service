package com.micro.system.controller;

import com.micro.base.common.dto.system.RoleDTO;
import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.system.convertor.RoleConvertor;
import com.micro.system.model.Role;
import com.micro.system.service.RoleService;
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
    public ResultData pageableSearch(SearchData searchData, Pageable pageable) {
        Page<Role> rolePage = roleService.pageableSearch(searchData, pageable);
        return ResultData.success("条件分页查询角色列表成功！", roleConvertor.toPageDTO(rolePage));
    }

    /**
     * 根据角色Id获取菜单Ids列表
     *
     * @param id 角色Id
     * @return 菜单列表Id
     */
    @ApiOperation(value = "根据角色Id获取菜单Ids列表")
    @GetMapping(value = "{id}/getMenuIdArrByRoleId")
    public ResultData getMenuIdArrByRoleId(@PathVariable Long id) {
        List<Long> menuIdList = roleService.getMenuIdArrByRoleId(id);
        return ResultData.success("根据角色id获取菜单Id列表成功！", menuIdList);
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
    public ResultData assignPermissions(@PathVariable Long roleId, @RequestBody List<Long> allMenuIds) {
        roleService.assignPermissions(roleId, allMenuIds);
        return ResultData.success("角色分配权限成功！");
    }

    /**
     * 保存角色
     *
     * @param roleDTO 角色
     * @return 保存角色是否成功
     */
    @ApiOperation(value = "保存角色")
    @PostMapping(value = "saveRole")
    public ResultData saveRole(@RequestBody RoleDTO roleDTO) {
        return roleService.saveRole(roleConvertor.toModel(roleDTO), roleDTO.getMenuIdArr());
    }

    /**
     * 批量删除角色成功
     *
     * @param ids 角色id列表
     * @return 批量删除成功
     */
    @ApiOperation(value = "批量删除角色成功")
    @DeleteMapping(value = "batchDeleteByIds")
    public ResultData batchDeleteByIds(Long[] ids) {
        return roleService.batchDeleteByIds(ids);
    }

    /**
     * 获取全部启用角色
     *
     * @return 全部启用角色
     */
    @ApiOperation(value = "获取全部启用角色")
    @GetMapping(value = "getAllRoleList")
    public ResultData getAllRoleList() {
        List<Role> roleList = roleService.getAllRoleList();
        return ResultData.success("获取所有角色成功！", roleConvertor.toListDTO(roleList));
    }

    /**
     * 更改角色状态
     *
     * @param roleDTO 角色id和角色enabled
     * @return 是否更改成功
     */
    @ApiOperation(value = "更改角色状态")
    @PutMapping(value = "changeRoleEnabled")
    public ResultData changeRoleEnabled(@RequestBody RoleDTO roleDTO) {
        return roleService.changeRoleEnabled(roleConvertor.toModel(roleDTO));
    }
}