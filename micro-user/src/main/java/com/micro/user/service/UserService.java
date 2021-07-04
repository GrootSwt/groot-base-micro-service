package com.micro.user.service;

import com.micro.common.util.ResultUtil;
import com.micro.user.model.User;

public interface UserService {

    User getUserByLoginName(String loginName);

    User getUserByLoginNameAndPassword(String loginName, String password);

    ResultUtil validateLoginInfoAndGenerateToken(User user);
}
