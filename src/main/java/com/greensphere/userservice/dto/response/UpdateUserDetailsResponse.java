package com.greensphere.userservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDetailsResponse {
    private String email;
    private String mobile;
    private String fullName;
}
