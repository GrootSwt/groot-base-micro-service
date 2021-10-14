package com.micro.user.convertor;

import com.micro.base.common.convertor.BaseConvertor;
import com.micro.common.dto.user.RoleDTO;
import com.micro.user.model.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoleConvertor extends BaseConvertor<Role, RoleDTO> {
    @Override
    public Role toModel(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return role;
    }

    @Override
    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }
}
