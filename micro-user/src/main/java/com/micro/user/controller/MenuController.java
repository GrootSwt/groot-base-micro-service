package com.micro.user.controller;

import com.micro.common.util.ResultUtil;
import com.micro.user.convertor.MenuConvertor;
import com.micro.user.model.Menu;
import com.micro.user.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping(value = "getAllMenu")
    public ResultUtil getAllMenu() {
        List<Menu> menuMap = menuService.getMapMenus();
        return ResultUtil.success("获取全部菜单成功！", menuMap);
    }
}
