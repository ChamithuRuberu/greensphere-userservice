package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.request.workout.CreateWorkoutRequest;
import com.greensphere.userservice.dto.request.workout.GetWorkoutsRequest;
import com.greensphere.userservice.dto.request.workout.UpdateWorkoutHistoryRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.workout.GetAllWorkoutsResponse;
import com.greensphere.userservice.dto.response.workout.UpcomingSchedulesResponse;
import com.greensphere.userservice.entity.AppUser;

public interface WorkoutService {
    BaseResponse<GetAllWorkoutsResponse> getWorkoutsByUsername(AppUser appUser, GetWorkoutsRequest request);

    BaseResponse<GetAllWorkoutsResponse> createWorkout(AppUser appUser, CreateWorkoutRequest request);

    BaseResponse<?> updateWorkoutHistory(AppUser appUser, UpdateWorkoutHistoryRequest request);

    BaseResponse<UpcomingSchedulesResponse> getUpcomingSchedulesForTrainer(AppUser appUser, int daysAhead);
}
