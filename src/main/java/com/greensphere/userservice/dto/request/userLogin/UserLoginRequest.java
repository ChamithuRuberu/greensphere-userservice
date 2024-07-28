package com.greensphere.userservice.dto.request.userLogin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role_type")
    private String roleType;
}
