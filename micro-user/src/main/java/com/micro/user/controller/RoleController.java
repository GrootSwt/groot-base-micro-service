package com.micro.user.controller;

import com.micro.common.dto.user.RoleDTO;
import com.micro.common.util.ResultUtil;
import com.micro.common.util.SearchData;
import com.micro.user.convertor.RoleConvertor;
import com.micro.user.model.Role;
import com.micro.user.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @GetMapping(value = "pageableSearch")
    public ResultUtil pageableSearch(SearchData searchData, Pageable pageable) {
        Page<Role> rolePage = roleService.pageableSearch(searchData, pageable);
        return ResultUtil.success("条件分页查询角色列表成功！", roleConvertor.toPageDTO(rolePage));
    }

    /**
     * 根据角色Id获取菜单Ids列表
     *
     * @param id 角色Id
     * @return 菜单列表Id
     */
    @GetMapping(value = "{id}/getMenuIdArrByRoleId")
    public ResultUtil getMenuIdArrByRoleId(@PathVariable Long id) {
        List<Long> menuIdList = roleService.getMenuIdArrByRoleId(id);
        return ResultUtil.success("根据角色id获取菜单Id列表成功！", menuIdList);
    }

    /**
     * 角色分配权限
     *
     * @param roleId     角色Id
     * @param allMenuIds 菜单Id列表
     * @return 角色分配权限是否成功
     */
    @PutMapping(value = "{roleId}/assignPermissions")
    public ResultUtil assignPermissions(@PathVariable Long roleId, @RequestBody List<Long> allMenuIds) {
        roleService.assignPermissions(roleId, allMenuIds);
        return ResultUtil.success("角色分配权限成功！");
    }

    /**
     * 保存角色
     *
     * @param roleDTO 角色
     * @return 保存角色是否成功
     */
    @PostMapping(value = "saveRole")
    public ResultUtil saveRole(@RequestBody RoleDTO roleDTO) {
        roleService.saveRole(roleConvertor.toModel(roleDTO));
        return ResultUtil.success("保存角色成功！");
    }

    /**
     * 批量删除角色成功
     *
     * @param ids 角色id列表
     * @return 批量删除成功
     */
    @DeleteMapping(value = "batchDeleteByIds")
    public ResultUtil batchDeleteByIds(Long[] ids) {
        roleService.batchDeleteByIds(ids);
        return ResultUtil.success("批量删除角色成功！");
    }
}
