package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.request.GymServiceRequest.GymRegisterRequest;
import com.greensphere.userservice.dto.response.BaseResponse;

import java.util.HashMap;

public interface GymService {
    BaseResponse<HashMap<String,Object>> registerGym(GymRegisterRequest gymRegisterRequest);

    BaseResponse<java.util.List<com.greensphere.userservice.entity.Gym>> getAllGyms();
}
