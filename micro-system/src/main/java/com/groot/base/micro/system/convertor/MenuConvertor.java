package com.groot.base.micro.system.convertor;


import com.groot.base.web.convertor.BaseConvertor;
import com.groot.base.micro.system.dto.MenuDTO;
import com.groot.base.micro.system.model.Menu;
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
