package com.greensphere.userservice.dto.request.GymServiceRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymRegisterRequest {

    private String gymName;
    private String location;
    private String email;
    private String phone;
    private String monthlyFee;
    private String membership;
    private int month;
    private String password;
    private Long adminId;
}
