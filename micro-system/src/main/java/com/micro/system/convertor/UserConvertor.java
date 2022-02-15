package com.micro.system.convertor;


import com.groot.base.web.convertor.BaseConvertor;
import com.micro.system.dto.UserDTO;
import com.micro.system.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor extends BaseConvertor<User, UserDTO> {

    @Override
    public User toModel(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
