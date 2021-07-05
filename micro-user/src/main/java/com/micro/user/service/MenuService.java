package com.micro.user.service;

import com.micro.user.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMapMenus();

    List<Menu> getMapMenusByRoleId(Long roleId);
}
