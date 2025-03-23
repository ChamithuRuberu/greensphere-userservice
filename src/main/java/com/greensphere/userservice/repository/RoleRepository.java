package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleById(long id);
    Role findRoleByName(String name);

    Optional<Role> findByName(String roleSuperAdmin);
}
