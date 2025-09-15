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
public class AdminIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long adminId;
    private Long gymId;
    private String userEmail;
    private int month;
    private Long userId;
    private Long trainerId;
    private LocalDate lastPaymentDate;
    private LocalDate nextPaymentDate;
    private BigDecimal amount;

}
