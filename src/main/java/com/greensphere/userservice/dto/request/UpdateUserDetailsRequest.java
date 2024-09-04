package com.greensphere.userservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDetailsRequest {
    private String fullname;
    private String mobile;
    private String email;

}
