package com.greensphere.userservice.dto.request.userRegister;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterVerifyRequest {

    private String username;
    private String otp;
}
