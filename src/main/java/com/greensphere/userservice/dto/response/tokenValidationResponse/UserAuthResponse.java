package com.greensphere.userservice.dto.response.tokenValidationResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthResponse {
    @JsonProperty("app_user")
    private UserResponse appUser;

}
