package com.micro.user.controller;

import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.ResultUtil;
import com.micro.common.util.SearchData;
import com.micro.user.convertor.UserConvertor;
import com.micro.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    @GetMapping(value = "pageableSearch")
    public ResultUtil pageableSearch(Pageable pageable, SearchData searchData) {
        return userService.pageableSearch(pageable, searchData);
    }

    /**
     * 批量删除用户操作
     *
     * @param idArr 用户ids
     * @return 是否删除成功
     */
    @DeleteMapping(value = "batchDelete")
    public ResultUtil batchDelete(Long[] idArr) {
        userService.batchDelete(idArr);
        return ResultUtil.success("批量删除成功！");
    }

    @PostMapping(value = "addOrEditUser")
    public ResultUtil addOrEditUser(@RequestBody UserDTO userDTO) {
        userService.addOrEditUser(userConvertor.toModel(userDTO));
        return ResultUtil.success("新增或编辑用户成功！");
    }

    /**
     * 更改用户enabled
     *
     * @param userDTO 用户id和用户enabled
     * @return 更改用户enabled是否成功
     */
    @PutMapping(value = "changeUserEnabled")
    public ResultUtil changeUserEnabled(@RequestBody UserDTO userDTO) {
        userService.changeUserEnabled(userConvertor.toModel(userDTO));
        return ResultUtil.success("更改用户启用状态成功！");
    }
}
