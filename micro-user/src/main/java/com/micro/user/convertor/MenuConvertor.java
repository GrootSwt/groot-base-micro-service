package com.micro.user.convertor;

import com.micro.base.common.convertor.BaseConvertor;
import com.micro.common.dto.user.MenuDTO;
import com.micro.user.model.Menu;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MenuConvertor extends BaseConvertor<Menu, MenuDTO> {

    @Override
    public Menu toModel(MenuDTO menuDTO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDTO, menu);
        return menu;
    }

    @Override
    public MenuDTO toDTO(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        return menuDTO;
    }
}
