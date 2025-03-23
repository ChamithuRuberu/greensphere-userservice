package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.request.workout.CreateWorkoutRequest;
import com.greensphere.userservice.dto.request.workout.GetWorkoutsRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.workout.GetAllWorkoutsResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.WorkOuts;
import com.greensphere.userservice.entity.WorkoutHistory;
import com.greensphere.userservice.repository.WorkoutsHistoryRepository;
import com.greensphere.userservice.repository.WorkoutsRepository;
import com.greensphere.userservice.service.WorkoutService;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutsRepository workoutsRepository;
    private final WorkoutsHistoryRepository workoutsHistoryRepository;

    @Override
    public BaseResponse<GetAllWorkoutsResponse> getWorkoutsByUsername(AppUser appUser, GetWorkoutsRequest request) {
        return null;
    }

    @Override
    public BaseResponse<GetAllWorkoutsResponse> createWorkout(AppUser appUser, CreateWorkoutRequest request) {

        if (request.getWorkouts() == null || request.getWorkouts().isEmpty()) {
            throw new IllegalArgumentException("Workouts list cannot be empty");
        }

        // Create one WorkoutHistory per workout session
        WorkoutHistory workoutHistory = new WorkoutHistory();
        workoutHistory.setTrainerId(String.valueOf(appUser.getGovId()));
        workoutHistory.setUserId(request.getClientId());
        workoutHistory = workoutsHistoryRepository.save(workoutHistory);

        List<WorkOuts> savedWorkouts = new ArrayList<>();

        for (CreateWorkoutRequest.WorkOuts workoutReq : request.getWorkouts()) {
            if (workoutReq == null || workoutReq.getExercises() == null) {
                continue;
            }

            for (CreateWorkoutRequest.WorkOuts.Exercise exercise : workoutReq.getExercises()) {
                WorkOuts workOuts = new WorkOuts();
                
                // Set basic workout information
                workOuts.setUsername(request.getClientId());
                workOuts.setTrainerId(String.valueOf(appUser.getGovId()));
                workOuts.setStartDateTime(workoutReq.getStartTime());  // Using the specific workout start time
                workOuts.setEndDateTime(calculateEndTime(workoutReq.getStartTime(), workoutReq.getDuration()));
                workOuts.setStatus("ACTIVE");

                // Set workout type and schedule details
                workOuts.setType(workoutReq.getType());
                workOuts.setDay(workoutReq.getDay());
                workOuts.setDuration(String.valueOf(workoutReq.getDuration()));
                workOuts.setNotes(workoutReq.getNotes());

                // Set exercise details
                workOuts.setName(exercise.getName());
                workOuts.setReps(String.valueOf(exercise.getReps()));
                workOuts.setSets(String.valueOf(exercise.getSets()));
                workOuts.setWeight(exercise.getWeight());
                workOuts.setExerciseNotes(exercise.getNotes());

                // Set the relationship with WorkoutHistory
                workOuts.setWorkoutHistory(workoutHistory);
                
                // Save the workout
                WorkOuts savedWorkout = workoutsRepository.save(workOuts);
                savedWorkouts.add(savedWorkout);
            }
        }

        // Create response with saved workouts
        GetAllWorkoutsResponse response = new GetAllWorkoutsResponse();
        response.setWorkouts(savedWorkouts);

        return BaseResponse.<GetAllWorkoutsResponse>builder()
                .code(ResponseUtil.SUCCESS)
                .message(ResponseUtil.SUCCESS)
                .data(response)
                .build();
    }

    private String calculateEndTime(String startTime, int durationMinutes) {
        try {
            // Parse the start time (assuming format "HH:mm")
            String[] timeParts = startTime.split(":");
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);
            
            // Add duration
            minutes += durationMinutes;
            hours += minutes / 60;
            minutes = minutes % 60;
            hours = hours % 24;  // Handle day overflow
            
            // Format back to string
            return String.format("%02d:%02d", hours, minutes);
        } catch (Exception e) {
            return startTime; // Return original if parsing fails
        }
    }

}
