package com.greensphere.userservice.dto.request.userRegister;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greensphere.userservice.utils.AppConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetUpDetailsRequest {

    @NotEmpty(message = AppConstants.USER_ID_REQUIRED)
    private String username;

    private String profile;

    @JsonProperty("full_name")
    @NotEmpty(message = "full_name shouldn't be empty")
    private String fullName;

    @JsonProperty("birth_of_date")
    @NotEmpty(message = "birth_of_date shouldn't be empty")
    private String birthOfDate;

    @JsonProperty("address_no")
    @NotEmpty(message = "Address no shouldn't be empty")
    private String addressNo;

    @JsonProperty("address_street")
    @NotEmpty(message = "Address street shouldn't be empty")
    private String addressStreet;

    @NotEmpty(message = "City shouldn't be empty")
    private String city;

    @NotEmpty(message = "password shouldn't be empty")
    private String password;

    @NotEmpty(message = "postal_code shouldn't be empty")
    private String postalCode;

    @JsonProperty("role_type")
    private String roleType;

    private String servicePeriod;
    private String weight;
    private String height;
    private String injuries;
    private String trainerId;
    private String chest;
    private String waist;
    private String neck;
    private Long gymId;
}
