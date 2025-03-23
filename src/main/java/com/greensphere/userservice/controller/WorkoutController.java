package com.greensphere.userservice.controller;

import com.greensphere.userservice.dto.request.workout.CreateWorkoutRequest;
import com.greensphere.userservice.dto.request.workout.GetWorkoutsRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.DefaultResponse;
import com.greensphere.userservice.dto.response.workout.GetAllWorkoutsResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.service.WorkoutService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/create-workouts")
    public ResponseEntity<DefaultResponse> createWorkout(@RequestAttribute("user") AppUser appUser, @RequestBody CreateWorkoutRequest request) {
        BaseResponse<GetAllWorkoutsResponse> response = workoutService.createWorkout(appUser, request);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }


    @PostMapping("/get-workouts")
    public ResponseEntity<DefaultResponse> getWorkoutsByUsername(@RequestAttribute("user") AppUser appUser, @RequestBody GetWorkoutsRequest request) {
        BaseResponse<GetAllWorkoutsResponse> response = workoutService.getWorkoutsByUsername(appUser, request);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }


}
