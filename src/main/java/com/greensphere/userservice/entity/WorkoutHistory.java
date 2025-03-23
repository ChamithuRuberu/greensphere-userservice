package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class WorkoutHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_history_sequence")
    @SequenceGenerator(name = "workout_history_sequence", sequenceName = "workout_history_sequence")
    private Long id;
    private String trainerId;
    private String userId;
    private String historyId = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "workoutHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkOuts> workouts = new ArrayList<>();
}
