package com.micro.system.convertor;

import com.micro.base.web.convertor.BaseConvertor;
import com.micro.base.common.dto.system.MenuDTO;
import com.micro.system.model.Menu;
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
