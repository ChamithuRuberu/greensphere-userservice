package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.entity.AppUser;

import java.util.HashMap;

public interface TrainerService {
    BaseResponse<HashMap<String, Object>> getAllTrainers();

    BaseResponse<HashMap<String, Object>> getClientsByTrainer(AppUser appUser);

    BaseResponse<HashMap<String, Object>> getTrainerForClient(AppUser appUser);
}
