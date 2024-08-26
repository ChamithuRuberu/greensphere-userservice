package com.greensphere.userservice.dto.response.tokenValidationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String username;
    private String nic;
    private String mobile;
    private String status;
    private String email;
    private String name;
    private String govId;
    private String dob;
    private String city;
    private String addressNo;

}

