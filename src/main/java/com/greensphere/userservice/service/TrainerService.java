package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.response.BaseResponse;

import java.util.HashMap;

public interface TrainerService {
    BaseResponse<HashMap<String, Object>> getAllTrainers();
}
