package com.greensphere.userservice.dto.request.workout;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateWorkoutRequest {

    private String clientId;
    private String programName;        // Name of the workout program
    private String programDescription; // Description of the program
    private String startDate;
    private String endDate;
    private String difficulty;         // BEGINNER, INTERMEDIATE, ADVANCED
    private String goal;              // STRENGTH, HYPERTROPHY, ENDURANCE, WEIGHT_LOSS, etc.
    private List<WorkoutWeek> weeks;  // Organize workouts by weeks for better progression

    @Getter
    @Setter
    public static class WorkoutWeek {
        private int weekNumber;
        private List<WorkoutDay> workoutDays;
        private String weeklyGoal;
        private String notes;
    }

    @Getter
    @Setter
    public static class WorkoutDay {
        private String day;            // MONDAY, TUESDAY, etc.
        private String focusArea;      // UPPER_BODY, LOWER_BODY, FULL_BODY, CARDIO, etc.
        private String startTime;
        private int duration;          // in minutes
        private List<Exercise> exercises;
        private String warmupNotes;
        private String cooldownNotes;
        private String generalNotes;
        private boolean isRestDay;
        private String intensity;      // LOW, MEDIUM, HIGH
    }

    @Getter
    @Setter
    public static class Exercise {
        private String name;
        private int sets;
        private int reps;
        private String weight;         // Can be actual weight or "bodyweight"
        private String equipment;      // Required equipment
        private String targetMuscles;  // Primary muscles targeted
        private String notes;          // Form cues, variations, etc.
        private String restBetweenSets;// Rest period between sets
        private String tempo;          // Exercise tempo (e.g., "2-0-2" for 2s down, 0s pause, 2s up)
        private boolean isDropSet;     // Whether this is a drop set
        private boolean isSuperSet;    // Whether this is part of a super set
        private String superSetGroup;  // Group identifier for super sets
        private String progressionStrategy; // How to progress (increase weight, reps, etc.)
    }
}
