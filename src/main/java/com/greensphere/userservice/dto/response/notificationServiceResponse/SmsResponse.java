package com.greensphere.userservice.dto.response.notificationServiceResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SmsResponse {
    private String code;
    private String message;
}
