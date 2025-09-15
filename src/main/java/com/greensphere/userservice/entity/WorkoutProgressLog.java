package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WorkoutProgressLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_history_id")
    private WorkoutHistory workoutHistory;

    private LocalDateTime workoutDate;
    private int weekNumber;
    private String day;
    
    // Performance Metrics
    private String exerciseName;
    private Integer completedSets;
    private Integer completedReps;
    private String weightUsed;
    private String intensity;
    private Integer difficultyRating; // 1-10 scale
    private String energyLevel;      // LOW, MEDIUM, HIGH
    private Integer performanceRating; // 1-10 scale
    
    // Feedback
    private String trainerNotes;
    private String clientFeedback;
    private String painPoints;
    private String improvements;
    private String modifications;
    
    // Recovery Metrics
    private Integer recoveryTime;    // in hours
    private String sorenessLevel;    // NONE, MILD, MODERATE, SEVERE
    private String sleepQuality;     // POOR, FAIR, GOOD, EXCELLENT
    private Integer stressLevel;     // 1-10 scale
    private String nutritionNotes;
    
    // Goals and Progress
    private Boolean goalsMet;
    private String progressNotes;
    private String nextSessionFocus;
} 