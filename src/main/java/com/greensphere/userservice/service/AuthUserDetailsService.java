package com.greensphere.userservice.service;

import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.AuthUser;
import com.greensphere.userservice.entity.Role;
import com.greensphere.userservice.repository.RoleRepository;
import com.greensphere.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser;
        try {
            appUser = getAppUserDetails(email);
            if (appUser == null) {
                throw new UsernameNotFoundException("User " + email + " was not found in the database");
            }

            return new AuthUser(appUser);
        } catch (Exception e) {
            log.error("loadUserByUsername-> Exception: {}", e.getMessage(), e);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
    }


    private AppUser getAppUserDetails(String email) {
        Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        AppUser appUser = null;

        if (email.contains("@")) {
            appUser = userRepository.findAppUserByEmail(email);

        } else {
            appUser = userRepository.findAppUserByUsername(email);
        }

        if (appUser != null) {
            Hibernate.initialize(appUser.getRoles());
            appUser.getRoles().forEach(role -> {
                Role role1 = roleRepository.findRoleById(role.getId());
                if (role1 != null) {
                    Hibernate.initialize(role1.getPermissions());
                    role1.getPermissions().forEach(permission -> {
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                        grantedAuthoritiesList.add(grantedAuthority);
                    });
                }
            });
            appUser.setGrantedAuthoritiesList(grantedAuthoritiesList);
            return appUser;
        } else {
            log.warn("getAppUserDetails-> user not found for this user identity : {}", email);
            return null;
        }
    }
}
