package com.greensphere.userservice.dto.response.userLoginResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserObj {
    @JsonProperty("full_name")
    private String fullName;
    private String email;
    @JsonProperty("gov_id")
    private Long govId;
    private String city;
    private String status;
    private String mobile;

}
