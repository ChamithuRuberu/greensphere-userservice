package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class TrainerIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_income_sequence")
    @SequenceGenerator(name = "trainer_income_sequence", sequenceName = "trainer_income_sequence", allocationSize = 1)
    private Long id;
    private Long trainerId;
    private String userEmail;
    private int month;
    private LocalDate lastPaymentDate;
    private LocalDate nextPaymentDate;
    private BigDecimal amount;

}
