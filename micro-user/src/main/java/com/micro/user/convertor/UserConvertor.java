package com.micro.user.convertor;

import com.micro.base.common.convertor.BaseConvertor;
import com.micro.common.dto.user.UserDTO;
import com.micro.user.model.User;
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
