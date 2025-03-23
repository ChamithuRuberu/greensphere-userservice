package com.greensphere.userservice.dto.request.workout;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UpdateWorkoutHistoryRequest {
    private String workoutHistoryId;  // ID of the workout program
    private String clientId;
    private Integer weekNumber;
    private String date;              // Date of the workout session
    
    private List<ExerciseProgress> exerciseProgresses;
    
    @Getter
    @Setter
    public static class ExerciseProgress {
        private String exerciseName;
        private String actualWeight;      // Weight used in this session
        private Integer actualSets;       // Sets completed
        private Integer actualReps;       // Reps completed
        private String performanceLevel;  // BETTER, SAME, WORSE
        private Integer difficultyLevel;  // 1-10 scale
        private String notes;            // Trainer's notes about the exercise
        private String modifications;     // Any modifications made to the exercise
        private String clientFeedback;    // Client's feedback about the exercise
        
        // Recovery and Form
        private String formQuality;       // EXCELLENT, GOOD, FAIR, POOR
        private String painLevel;         // NONE, MILD, MODERATE, SEVERE
        private String rangeOfMotion;     // FULL, PARTIAL, LIMITED
        private String technicalNotes;    // Notes about technique
        
        // Energy and Performance
        private String energyLevel;       // HIGH, MEDIUM, LOW
        private String focusLevel;        // HIGH, MEDIUM, LOW
        private Integer rpe;              // Rate of Perceived Exertion (1-10)
        
        // Progress Indicators
        private Boolean increasedWeight;
        private Boolean increasedReps;
        private Boolean improvedForm;
        private String progressNotes;
    }
    
    // Overall Session Details
    private String sessionDuration;
    private String overallIntensity;     // HIGH, MEDIUM, LOW
    private String sessionNotes;
    private String nextSessionFocus;
    private List<String> areasOfImprovement;
    
    // Recovery Metrics
    private String postWorkoutFeeling;   // ENERGIZED, TIRED, EXHAUSTED
    private Integer recoveryNeeded;      // Hours needed for recovery
    private String sleepQuality;         // GOOD, FAIR, POOR
    private String nutritionNotes;
    private String hydrationLevel;       // GOOD, FAIR, POOR
    
    // Trainer's Overall Assessment
    private String trainerAssessment;
    private List<String> recommendations;
    private Boolean programAdjustmentNeeded;
    private String adjustmentNotes;
} 