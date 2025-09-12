package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.request.workout.CreateWorkoutRequest;
import com.greensphere.userservice.dto.request.workout.GetWorkoutsRequest;
import com.greensphere.userservice.dto.request.workout.UpdateWorkoutHistoryRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.workout.GetAllWorkoutsResponse;
import com.greensphere.userservice.dto.response.workout.UpcomingScheduleDTO;
import com.greensphere.userservice.dto.response.workout.UpcomingSchedulesResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.WorkOuts;
import com.greensphere.userservice.entity.WorkoutHistory;
import com.greensphere.userservice.entity.WorkoutProgressLog;
import com.greensphere.userservice.repository.WorkoutProgressLogRepository;
import com.greensphere.userservice.repository.WorkoutsHistoryRepository;
import com.greensphere.userservice.repository.WorkoutsRepository;
import com.greensphere.userservice.service.WorkoutService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutsRepository workoutsRepository;
    private final WorkoutsHistoryRepository workoutsHistoryRepository;
    private final WorkoutProgressLogRepository workoutProgressLogRepository;

    @Override
    public BaseResponse<GetAllWorkoutsResponse> getWorkoutsByUsername(AppUser appUser, GetWorkoutsRequest request) {
        return null;
    }

    @Override
    public BaseResponse<GetAllWorkoutsResponse> createWorkout(AppUser appUser, CreateWorkoutRequest request) {
        try {
            if (request.getWeeks() == null || request.getWeeks().isEmpty()) {
                throw new IllegalArgumentException("Workout program must include at least one week");
            }

            // Create workout history for the program
            WorkoutHistory workoutHistory = new WorkoutHistory();
            workoutHistory.setTrainerId(String.valueOf(appUser.getGovId()));
            workoutHistory.setUserId(request.getClientId());
            workoutHistory.setProgramName(request.getProgramName());
            workoutHistory.setProgramDescription(request.getProgramDescription());
            workoutHistory.setDifficulty(request.getDifficulty());
            workoutHistory.setGoal(request.getGoal());
            
            // Convert string dates to LocalDateTime
            try {
                workoutHistory.setProgramStartDate(request.getStartDate());
                workoutHistory.setProgramEndDate(request.getEndDate());
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Use ISO format (yyyy-MM-dd'T'HH:mm:ss)");
            }
            
            workoutHistory.setStatus("ACTIVE");
            workoutHistory.setCurrentWeek(1);
            workoutHistory.setLastUpdated(LocalDateTime.now());
            
            // Save workout history first
            try {
                workoutHistory = workoutsHistoryRepository.save(workoutHistory);
            } catch (Exception e) {
                throw new RuntimeException("Failed to save workout history: " + e.getMessage(), e);
            }

            List<WorkOuts> savedWorkouts = new ArrayList<>();

            // Process each week in the program
            for (CreateWorkoutRequest.WorkoutWeek week : request.getWeeks()) {
                // Process each day in the week
                for (CreateWorkoutRequest.WorkoutDay workoutDay : week.getWorkoutDays()) {
                    if (workoutDay.isRestDay()) {
                        // Create a rest day workout entry
                        WorkOuts restDay = new WorkOuts();
                        restDay.setWeekNumber(week.getWeekNumber());
                        restDay.setDay(workoutDay.getDay());
                        restDay.setIsRestDay(true);
                        restDay.setStatus("PLANNED");
                        restDay.setUsername(request.getClientId());
                        restDay.setTrainerId(String.valueOf(appUser.getGovId()));
                        restDay.setWorkoutHistory(workoutHistory);
                        savedWorkouts.add(workoutsRepository.save(restDay));
                        continue;
                    }

                    // Process exercises for training days
                    for (CreateWorkoutRequest.Exercise exercise : workoutDay.getExercises()) {
                        WorkOuts workOuts = new WorkOuts();
                        
                        // Set program context
                        workOuts.setWeekNumber(week.getWeekNumber());
                        workOuts.setFocusArea(workoutDay.getFocusArea());
                        workOuts.setIntensity(workoutDay.getIntensity());
                        
                        // Set workout schedule
                        workOuts.setType(exercise.getTargetMuscles());
                        workOuts.setDay(workoutDay.getDay());
                        workOuts.setDuration(String.valueOf(workoutDay.getDuration()));
                        workOuts.setWarmupNotes(workoutDay.getWarmupNotes());
                        workOuts.setCooldownNotes(workoutDay.getCooldownNotes());
                        workOuts.setGeneralNotes(workoutDay.getGeneralNotes());
                        workOuts.setIsRestDay(false);
                        
                        // Set exercise details
                        workOuts.setName(exercise.getName());
                        workOuts.setSets(String.valueOf(exercise.getSets()));
                        workOuts.setReps(String.valueOf(exercise.getReps()));
                        workOuts.setWeight(exercise.getWeight());
                        workOuts.setEquipment(exercise.getEquipment());
                        workOuts.setTargetMuscles(exercise.getTargetMuscles());
                        workOuts.setExerciseNotes(exercise.getNotes());
                        workOuts.setRestBetweenSets(exercise.getRestBetweenSets());
                        workOuts.setTempo(exercise.getTempo());
                        workOuts.setIsDropSet(exercise.isDropSet());
                        workOuts.setIsSuperSet(exercise.isSuperSet());
                        workOuts.setSuperSetGroup(exercise.getSuperSetGroup());
                        workOuts.setProgressionStrategy(exercise.getProgressionStrategy());

                        // Set basic information
                        workOuts.setUsername(request.getClientId());
                        workOuts.setTrainerId(String.valueOf(appUser.getGovId()));
                        workOuts.setStartDateTime(workoutDay.getStartTime());
                        workOuts.setEndDateTime(calculateEndTime(workoutDay.getStartTime(), workoutDay.getDuration()));
                        workOuts.setStatus("PLANNED");
                        workOuts.setWorkoutHistory(workoutHistory);
                        
                        // Save and collect the workout
                        savedWorkouts.add(workoutsRepository.save(workOuts));
                    }
                }
            }

            // Create initial progress log
            WorkoutProgressLog initialLog = new WorkoutProgressLog();
            initialLog.setWorkoutHistory(workoutHistory);
            initialLog.setWorkoutDate(LocalDateTime.now());
            initialLog.setWeekNumber(1);
            initialLog.setProgressNotes("Workout program initialized");
            initialLog.setNextSessionFocus("Begin with Week 1 as planned");
            workoutProgressLogRepository.save(initialLog);

            // Create response with saved workouts
            GetAllWorkoutsResponse response = new GetAllWorkoutsResponse();
            response.setWorkouts(savedWorkouts);

            return BaseResponse.<GetAllWorkoutsResponse>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message("Workout program initialized")
                    .data(response)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<GetAllWorkoutsResponse>builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .message(ResponseUtil.FAILED)
                    .title("Failed to create workout: " + e.getMessage())
                    .build();
        }
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

    @Override
    public BaseResponse<?> updateWorkoutHistory(AppUser appUser, UpdateWorkoutHistoryRequest request) {
        // Validate request
        if (request.getWorkoutHistoryId() == null || request.getExerciseProgresses() == null) {
            throw new IllegalArgumentException("Workout history ID and exercise progresses are required");
        }

        // Get the workout history
        WorkoutHistory workoutHistory = workoutsHistoryRepository.findByHistoryId(request.getWorkoutHistoryId())
                .orElseThrow(() -> new IllegalArgumentException("Workout history not found"));

        // Verify trainer has access to this workout
        if (!workoutHistory.getTrainerId().equals(String.valueOf(appUser.getGovId()))) {
            throw new IllegalArgumentException("Trainer does not have access to this workout history");
        }

        // Update workout history progress
        workoutHistory.setCurrentWeek(request.getWeekNumber());
        workoutHistory.setLastUpdated(LocalDateTime.now());

        // Create a base progress log for the session
        WorkoutProgressLog sessionSummaryLog = new WorkoutProgressLog();
        sessionSummaryLog.setWorkoutHistory(workoutHistory);
        sessionSummaryLog.setWorkoutDate(LocalDateTime.parse(request.getDate()));
        sessionSummaryLog.setWeekNumber(request.getWeekNumber());
        sessionSummaryLog.setDay(LocalDateTime.parse(request.getDate()).getDayOfWeek().toString());
        
        // Set overall session details
        sessionSummaryLog.setPerformanceRating(calculateOverallPerformance(request.getExerciseProgresses()));
        sessionSummaryLog.setEnergyLevel(request.getOverallIntensity());
        sessionSummaryLog.setTrainerNotes(request.getSessionNotes());
        sessionSummaryLog.setNextSessionFocus(request.getNextSessionFocus());
        
        // Set recovery metrics
        sessionSummaryLog.setRecoveryTime(request.getRecoveryNeeded());
        sessionSummaryLog.setSleepQuality(request.getSleepQuality());
        sessionSummaryLog.setNutritionNotes(request.getNutritionNotes());
        sessionSummaryLog.setExerciseName("SESSION_SUMMARY"); // Mark this as a session summary
        
        // Save the session summary log
        workoutProgressLogRepository.save(sessionSummaryLog);
        
        // Process each exercise progress
        for (UpdateWorkoutHistoryRequest.ExerciseProgress progress : request.getExerciseProgresses()) {
            // Find the corresponding workout
            WorkOuts workout = workoutsRepository.findByWorkoutHistoryAndNameAndWeekNumber(
                    workoutHistory, progress.getExerciseName(), request.getWeekNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Workout not found: " + progress.getExerciseName()));
            
            // Update workout with actual performance
            workout.setWeight(progress.getActualWeight());
            workout.setReps(String.valueOf(progress.getActualReps()));
            workout.setSets(String.valueOf(progress.getActualSets()));
            workout.setExerciseNotes(progress.getNotes());
            workout.setStatus("COMPLETED");
            
            // Save updated workout
            workoutsRepository.save(workout);
            
            // Create exercise-specific progress log
            WorkoutProgressLog exerciseLog = new WorkoutProgressLog();
            exerciseLog.setWorkoutHistory(workoutHistory);
            exerciseLog.setWorkoutDate(LocalDateTime.parse(request.getDate()));
            exerciseLog.setWeekNumber(request.getWeekNumber());
            exerciseLog.setDay(LocalDateTime.parse(request.getDate()).getDayOfWeek().toString());
            exerciseLog.setExerciseName(progress.getExerciseName());
            exerciseLog.setCompletedSets(progress.getActualSets());
            exerciseLog.setCompletedReps(progress.getActualReps());
            exerciseLog.setWeightUsed(progress.getActualWeight());
            exerciseLog.setDifficultyRating(progress.getDifficultyLevel());
            exerciseLog.setClientFeedback(progress.getClientFeedback());
            exerciseLog.setPainPoints(progress.getPainLevel());
            exerciseLog.setImprovements(progress.getProgressNotes());
            exerciseLog.setModifications(progress.getModifications());
            exerciseLog.setEnergyLevel(progress.getEnergyLevel());
            exerciseLog.setPerformanceRating(progress.getRpe());
            
            // Save exercise-specific log
            workoutProgressLogRepository.save(exerciseLog);
        }
        
        // Update overall workout history status if needed
        updateWorkoutHistoryStatus(workoutHistory, request);
        workoutsHistoryRepository.save(workoutHistory);

        return BaseResponse.builder()
                .code(ResponseUtil.SUCCESS)
                .message("Workout history updated successfully")
                .data(Map.of(
                    "weekNumber", request.getWeekNumber(),
                    "exercisesUpdated", request.getExerciseProgresses().size(),
                    "lastUpdated", LocalDateTime.now()
                ))
                .build();
    }

    private Integer calculateOverallPerformance(List<UpdateWorkoutHistoryRequest.ExerciseProgress> progresses) {
        return (int) progresses.stream()
                .mapToInt(p -> p.getDifficultyLevel())
                .average()
                .orElse(0.0);
    }

    @Override
    public BaseResponse<UpcomingSchedulesResponse> getUpcomingSchedulesForTrainer(AppUser appUser, int daysAhead) {
        try {
            String trainerId = String.valueOf(appUser.getGovId());
            int windowDays = Math.max(daysAhead, 0);

            List<WorkOuts> planned = workoutsRepository.findByTrainerIdAndStatus(trainerId, "PLANNED");

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime windowEnd = now.plusDays(windowDays);

            // Build result grouped by userId (username field stores clientId)
            java.util.Map<String, java.util.List<UpcomingScheduleDTO>> grouped = new java.util.HashMap<>();

            for (WorkOuts w : planned) {
                // Compute next occurrence datetime from programStartDate + week/day + startTime
                LocalDateTime occurrence = computeOccurrenceDateTime(w);
                if (occurrence == null) {
                    continue;
                }
                if (occurrence.isBefore(now) || occurrence.isAfter(windowEnd)) {
                    continue;
                }

                UpcomingScheduleDTO dto = new UpcomingScheduleDTO();
                dto.setUserId(w.getUsername());
                dto.setWorkoutName(w.getName());
                dto.setDay(w.getDay());
                dto.setStartTime(w.getStartDateTime());
                dto.setEndTime(w.getEndDateTime());
                dto.setWeekNumber(w.getWeekNumber());
                dto.setStatus(w.getStatus());

                grouped.computeIfAbsent(w.getUsername(), k -> new java.util.ArrayList<>()).add(dto);
            }

            UpcomingSchedulesResponse data = new UpcomingSchedulesResponse();
            data.setUserSchedules(grouped);

            return BaseResponse.<UpcomingSchedulesResponse>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message("Upcoming schedules fetched")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<UpcomingSchedulesResponse>builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .title(ResponseUtil.FAILED)
                    .message("Failed to fetch upcoming schedules: " + e.getMessage())
                    .build();
        }
    }

    private LocalDateTime computeOccurrenceDateTime(WorkOuts w) {
        try {
            WorkoutHistory history = w.getWorkoutHistory();
            if (history == null || history.getProgramStartDate() == null) {
                return null;
            }

            // Parse programStartDate as LocalDate
            LocalDate programStart = LocalDate.parse(history.getProgramStartDate());

            // weekNumber starts at 1; compute the week's Monday
            LocalDate weekStart = programStart.plusWeeks(Math.max(w.getWeekNumber() - 1, 0));

            // Map day string to DayOfWeek
            DayOfWeek dow = parseDayOfWeek(w.getDay());
            if (dow == null) {
                return null;
            }

            // Adjust to target day in that week
            LocalDate targetDate = weekStart.with(dow);

            // Parse start time HH:mm if available; otherwise default 08:00
            String time = w.getStartDateTime();
            if (time == null || !time.matches("^\\d{2}:\\d{2}$")) {
                time = "08:00";
            }
            DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
            java.time.LocalTime lt = java.time.LocalTime.parse(time, tf);

            return LocalDateTime.of(targetDate, lt);
        } catch (Exception ex) {
            return null;
        }
    }

    private DayOfWeek parseDayOfWeek(String day) {
        if (day == null) return null;
        String d = day.trim().toUpperCase();
        switch (d) {
            case "MONDAY":
                return DayOfWeek.MONDAY;
            case "TUESDAY":
                return DayOfWeek.TUESDAY;
            case "WEDNESDAY":
                return DayOfWeek.WEDNESDAY;
            case "THURSDAY":
                return DayOfWeek.THURSDAY;
            case "FRIDAY":
                return DayOfWeek.FRIDAY;
            case "SATURDAY":
                return DayOfWeek.SATURDAY;
            case "SUNDAY":
                return DayOfWeek.SUNDAY;
            default:
                return null;
        }
    }

    private void updateWorkoutHistoryStatus(WorkoutHistory history, UpdateWorkoutHistoryRequest request) {
        if (Boolean.TRUE.equals(request.getProgramAdjustmentNeeded())) {
            history.setStatus("NEEDS_ADJUSTMENT");
            history.setPerformanceNotes(request.getAdjustmentNotes());
        }
        
        // Update progress metrics
        if (request.getAreasOfImprovement() != null) {
            history.setAchievedGoals(String.join(", ", request.getAreasOfImprovement()));
        }
        if (request.getRecommendations() != null) {
            history.setChallengesFaced(String.join(", ", request.getRecommendations()));
        }
    }

}
