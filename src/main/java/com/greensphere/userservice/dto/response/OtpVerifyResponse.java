package com.greensphere.userservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OtpVerifyResponse {

    private String code;
    private String message;
}
