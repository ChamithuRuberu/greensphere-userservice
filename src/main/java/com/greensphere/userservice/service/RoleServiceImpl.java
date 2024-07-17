package com.greensphere.userservice.service;

import com.greensphere.userservice.entity.Role;
import com.greensphere.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}
