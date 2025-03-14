package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.request.workout.GetWorkoutsRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.workout.GetAllWorkoutsResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    @Override
    public BaseResponse<GetAllWorkoutsResponse> getWorkoutsByUsername(AppUser appUser, GetWorkoutsRequest request) {
        return null;
    }
}
