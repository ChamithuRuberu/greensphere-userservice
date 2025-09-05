package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_sequence")
    @SequenceGenerator(name = "gym_sequence", sequenceName = "gym_sequence", allocationSize = 1)
    private Long id;
    private Long AdminId;
    private String gymName;
    private String email;
    private String phone;
    private String monthlyFee;
    private String membership;
}
