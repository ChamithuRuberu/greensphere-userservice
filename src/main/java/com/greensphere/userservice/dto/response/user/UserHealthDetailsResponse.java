package com.greensphere.userservice.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserHealthDetailsResponse {
    private String height;
    private String weight;
    private String injuries;
}


