package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class GymIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_income_sequence")
    @SequenceGenerator(name = "gym_income_sequence", sequenceName = "gym_income_sequence", allocationSize = 1)
    private Long id;
    private String gymId;
    private String userId;
    private String month;
    private String year;
    private BigDecimal amount;

}
