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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long trainerId;
    private String userEmail;
    private String userName;
    private int month;
    private LocalDate lastPaymentDate;
    private LocalDate nextPaymentDate;
    private BigDecimal amount;

}
