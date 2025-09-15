package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "workout_history")
public class WorkoutHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String historyId = UUID.randomUUID().toString();
    
    @Column(nullable = false)
    private String trainerId;
    
    @Column(nullable = false)
    private String userId;
    
    // Program Details
    @Column(nullable = false)
    private String programName;
    
    @Column(columnDefinition = "TEXT")
    private String programDescription;
    
    @Column(nullable = false)
    private String difficulty;
    
    @Column(nullable = false)
    private String goal;
    
    @Column(nullable = false)
    private String programStartDate;
    
    @Column(nullable = false)
    private String programEndDate;
    
    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, PAUSED
    
    // Progress Tracking
    @Column(nullable = false)
    private int currentWeek = 1;
    
    @Column(columnDefinition = "TEXT")
    private String achievedGoals = "";
    
    @Column(columnDefinition = "TEXT")
    private String challengesFaced = "";
    
    @Column
    private Double weightProgress = 0.0;    // Weight change during program
    
    @Column
    private Double strengthProgress = 0.0;  // Strength improvement percentage
    
    @Column(columnDefinition = "TEXT")
    private String performanceNotes = "";
    
    @Column(nullable = false)
    private LocalDateTime lastUpdated;
    
    @OneToMany(mappedBy = "workoutHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<WorkOuts> workouts = new ArrayList<>();
    
    @OneToMany(mappedBy = "workoutHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutProgressLog> progressLogs = new ArrayList<>();
    

}
