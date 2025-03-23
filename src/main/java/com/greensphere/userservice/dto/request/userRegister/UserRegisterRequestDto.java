package com.greensphere.userservice.dto.request.userRegister;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDto {
    private String nic;
    private String mobile;
    private String email;
    @NotEmpty(message = "roleType should not be empty")
    @JsonProperty("role_type")
    private String roleType;
    @JsonProperty("trainer_id")
    private Long govId;
}
