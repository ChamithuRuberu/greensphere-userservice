package com.greensphere.userservice.entity;

import com.greensphere.userservice.entity.WorkoutHistory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class WorkOuts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Program Context
    private int weekNumber;
    private String focusArea;      // UPPER_BODY, LOWER_BODY, etc.
    private String intensity;      // LOW, MEDIUM, HIGH
    
    // Exercise Details
    private String name;
    private String sets;
    private String reps;
    private String weight;
    private String equipment;
    private String targetMuscles;
    private String exerciseNotes;
    private String restBetweenSets;
    private String tempo;
    private Boolean isDropSet;
    private Boolean isSuperSet;
    private String superSetGroup;
    private String progressionStrategy;
    
    // Workout Schedule
    private String type;           // e.g., "Strength", "Cardio"
    private String day;            // e.g., "Monday", "Wednesday"
    private String duration;       // Duration in minutes
    private String warmupNotes;
    private String cooldownNotes;
    private String generalNotes;
    private Boolean isRestDay;
    
    // Status and User Info
    private String status;         // PLANNED, COMPLETED, SKIPPED
    private String username;
    private String trainerId;
    private String startDateTime;
    private String endDateTime;

    @ManyToOne
    @JoinColumn(name = "workout_history_id")
    @JsonBackReference
    private WorkoutHistory workoutHistory;
}
