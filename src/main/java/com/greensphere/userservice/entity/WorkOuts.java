package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkOuts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "workout_sequence")
    @SequenceGenerator(name = "workout_sequence",sequenceName = "workout_sequence")
    private Long id;
    private String name;
    private String description;
    private String reps;
    private String sets;
    private String weight;
    private String status;
    private String workoutHistoryId;
}
