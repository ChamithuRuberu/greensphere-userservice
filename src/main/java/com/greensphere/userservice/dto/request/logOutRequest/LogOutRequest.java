package com.greensphere.userservice.dto.request.logOutRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutRequest {
    @NotEmpty(message = "token shouldn't be empty")
    @JsonProperty("token")
    private String token;
}
