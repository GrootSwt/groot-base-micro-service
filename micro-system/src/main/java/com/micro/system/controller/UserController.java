package com.micro.system.controller;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.base.common.dto.system.UserDTO;
import com.micro.system.bean.ChangePasswordBean;
import com.micro.system.convertor.UserConvertor;
import com.micro.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {"用户"})
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserConvertor userConvertor;

    /**
     * 分页条件查询用户信息
     *
     * @param pageable   分页条件
     * @param searchData 查询条件
     * @return 用户列表
     */
    @ApiOperation(value = "分页查询用户列表")
    @GetMapping(value = "pageableSearch")
    public ResultData pageableSearch(Pageable pageable, SearchData searchData) {
        return userService.pageableSearch(pageable, searchData);
    }

    /**
     * 批量删除用户操作
     *
     * @param idArr 用户ids
     * @return 是否删除成功
     */
    @ApiOperation(value = "批量删除用户操作")
    @DeleteMapping(value = "batchDelete")
    public ResultData batchDelete(Long[] idArr) {
        userService.batchDelete(idArr);
        return ResultData.success("批量删除成功！");
    }

    @ApiOperation(value = "添加或者编辑用户（管理员修改）")
    @PostMapping(value = "addOrEditUser")
    public ResultData addOrEditUser(@RequestBody UserDTO userDTO) {
        userService.addOrEditUser(userConvertor.toModel(userDTO));
        return ResultData.success("新增或编辑用户成功！");
    }

    /**
     * 更改用户enabled
     *
     * @param userDTO 用户id和用户enabled
     * @return 更改用户enabled是否成功
     */
    @ApiOperation(value = "更改用户enabled")
    @PutMapping(value = "changeUserEnabled")
    public ResultData changeUserEnabled(@RequestBody UserDTO userDTO) {
        userService.changeUserEnabled(userConvertor.toModel(userDTO));
        return ResultData.success("更改用户启用状态成功！");
    }

    /**
     * 用户授权
     *
     * @param userDTO 用户DTO
     * @return 是否授权成功
     */
    @ApiOperation(value = "用户授权")
    @PutMapping(value = "authorization")
    public ResultData authorization(@RequestBody UserDTO userDTO) {
        return userService.authorization(userConvertor.toModel(userDTO));
    }

    /**
     * 更改用户信息
     *
     * @param userDTO 用户DTO
     * @return 更改后的用户信息
     */
    @ApiOperation(value = "更改用户信息（用户自己修改）")
    @PutMapping(value = "modifyUserInfo")
    public ResultData modifyUserInfo(@RequestBody UserDTO userDTO) {
        return userService.modifyUserInfo(userConvertor.toModel(userDTO));
    }


    /**
     * 更改头像
     *
     * @param userDTO 用户DTO
     * @return 更改后的用户信息
     */
    @ApiOperation(value = "更改头像")
    @PutMapping(value = "modifyAvatar")
    public ResultData modifyAvatar(@RequestBody UserDTO userDTO) {
        return userService.modifyAvatar(userConvertor.toModel(userDTO));
    }

    /**
     * 更改密码
     *
     * @param changePasswordBean 更改密码
     * @return 更改密码是否成功
     */
    @ApiOperation(value = "更改密码")
    @PutMapping(value = "changePassword")
    public ResultData changePassword(@RequestBody ChangePasswordBean changePasswordBean) {
        return userService.changePassword(changePasswordBean);
    }
}
