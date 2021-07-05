package com.micro.user.convertor;

import com.micro.common.dto.user.MenuDTO;
import com.micro.user.model.Menu;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuConvertor {

    public Menu toModel(MenuDTO dto) {
        Menu model = new Menu();
        BeanUtils.copyProperties(dto, model);
        return model;
    }

    public MenuDTO toDto(Menu model) {
        MenuDTO dto = new MenuDTO();
        BeanUtils.copyProperties(model, dto);
        return dto;
    }
}
