package com.micro.user.convertor;

import com.micro.common.dto.user.UserDTO;
import com.micro.user.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConvertor {

    public User toModel(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
