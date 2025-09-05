package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class TrainerIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_income_sequence")
    @SequenceGenerator(name = "trainer_income_sequence", sequenceName = "trainer_income_sequence", allocationSize = 1)
    private Long id;
    private String trainerId;
    private String userId;
    private String month;
    private String year;
    private BigDecimal amount;

}
