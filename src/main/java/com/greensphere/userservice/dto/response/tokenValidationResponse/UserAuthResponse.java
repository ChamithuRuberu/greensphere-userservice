package com.greensphere.userservice.dto.response.tokenValidationResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthResponse {
    @JsonProperty("app_user")
    private UserResponse appUser;
    @JsonProperty("user_details")
    private UserDetails userDetails;

}
