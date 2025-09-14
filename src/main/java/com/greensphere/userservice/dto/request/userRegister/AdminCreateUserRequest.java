package com.greensphere.userservice.dto.request.userRegister;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateUserRequest {
    private String email;
    private String mobile;
    private String nic;
    private String fullName;
    private String password;
    private String city;
    private String height;
    private String weight;
    private String injuries;
    private Long trainerGovId; // assign trainer
    private Long gymId;        // optional gym
}


