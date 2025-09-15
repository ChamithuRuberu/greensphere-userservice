package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String trainerId;
    private String servicePeriod;
    private String weight;
    private String height;
    private String profile;
    private String mobile;
    private String email;
    private String location;
    private String rating;
}
