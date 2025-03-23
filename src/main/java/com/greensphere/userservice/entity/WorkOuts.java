package com.greensphere.userservice.entity;

import com.greensphere.userservice.entity.WorkoutHistory;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_sequence")
    @SequenceGenerator(name = "workout_sequence", sequenceName = "workout_sequence")
    private Long id;
    
    // Exercise details
    private String name;
    private String reps;
    private String sets;
    private String weight;
    private String exerciseNotes;  // Added for exercise-specific notes
    
    // Workout schedule details
    private String type;           // e.g., "Strength", "Cardio"
    private String day;            // e.g., "Monday", "Wednesday"
    private String duration;       // Duration in minutes
    private String notes;          // General workout notes
    
    // Status and user info
    private String status;
    private String username;
    private String trainerId;
    private String startDateTime;
    private String endDateTime;

    @ManyToOne
    @JoinColumn(name = "workout_history_id")
    private WorkoutHistory workoutHistory;
}
