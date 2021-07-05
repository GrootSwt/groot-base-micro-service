package com.micro.user.service.impl;

import com.micro.user.model.Role;
import com.micro.user.repository.RoleRepository;
import com.micro.user.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;
    @Override
    public Role findFirstById(Long roleId) {
        return roleRepository.findFirstById(roleId);
    }
}
