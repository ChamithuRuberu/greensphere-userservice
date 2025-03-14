package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_sequence")
    @SequenceGenerator(name = "trainer_sequence", sequenceName = "trainer_sequence", allocationSize = 1)
    private Long id;
    private String name;
    private String trainerId;
    private String servicePeriod;
    private String weight;
    private String height;
    private String profile;
}
