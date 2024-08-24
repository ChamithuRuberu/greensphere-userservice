package com.greensphere.userservice.dto.request.userRegister;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterVerifyRequest {

    private String username;
    @NotEmpty(message = "otp should not be empty")
    private String otp;
}
