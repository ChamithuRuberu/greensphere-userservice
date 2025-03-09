package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkoutHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "workout_history_sequence")
    @SequenceGenerator(name = "workout_history_sequence", sequenceName = "workout_history_sequence")
    private Long id;
    private String weight;
    private String reps;
    private String sets;
    private String workoutId;
    private String trainerId;
    private String userId;
}
