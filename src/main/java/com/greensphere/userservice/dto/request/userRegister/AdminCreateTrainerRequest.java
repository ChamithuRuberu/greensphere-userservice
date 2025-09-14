package com.greensphere.userservice.dto.request.userRegister;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateTrainerRequest {
    private String email;
    private String mobile;
    private String nic;
    private String fullName;
    private String password;
    private String city;
    private String height;
    private String weight;
    private String profile;
    private String servicePeriod;
    private Long trainerGovId; // stored on user.govId and used as trainerId
    private Long gymId;        // optional
}


