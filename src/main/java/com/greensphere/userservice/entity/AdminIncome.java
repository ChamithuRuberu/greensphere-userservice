package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class AdminIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_income_sequence")
    @SequenceGenerator(name = "admin_income_sequence", sequenceName = "admin_income_sequence", allocationSize = 1)
    private Long id;
    private String adminId;
    private String gymId;
    private String month;
    private String year;
    private BigDecimal amount;

}
