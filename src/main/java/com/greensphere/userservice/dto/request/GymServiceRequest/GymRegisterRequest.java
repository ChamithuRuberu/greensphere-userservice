package com.greensphere.userservice.dto.request.GymServiceRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GymRegisterRequest {

    private String gymName;
    private String location;
    private String email;
    private String phone;
    @JsonProperty("amount")
    private String monthlyFee;
    private String membership;
    private int month;
    private String password;
    private Long adminId;
}
