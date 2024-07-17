package com.greensphere.userservice.entity;

public class AuthUser extends org.springframework.security.core.userdetails.User {


    public AuthUser(AppUser appUser) {
        super(appUser.getEmail(), appUser.getPassword(), appUser.getGrantedAuthoritiesList());
    }
}
