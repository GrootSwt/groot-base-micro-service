package com.micro.user.service;

import com.micro.user.model.Role;

public interface RoleService {

    Role findFirstById(Long roleId);
}
