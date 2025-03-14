package com.greensphere.userservice.dto.request.userRegister;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GovUserRegisterRequest {

    private String username;
    @Pattern(regexp = "^(?![ .]+$)[a-zA-Z .]*$", message = "Please enter a valid name")
    private String name;
    private String city;
    @NotEmpty(message = "password shouldn't be empty")
    private String password;
    @JsonProperty("role_type")
    private String roleType;
    private String servicePeriod;
    private String weight;
    private String height;
    private String profile;
    private String trainerId;

}
