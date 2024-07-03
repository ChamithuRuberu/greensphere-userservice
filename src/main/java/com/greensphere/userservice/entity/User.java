package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;
    @Builder.Default
    private String userId = String.valueOf(UUID.randomUUID());
    private String appUserId;
    private String email;
    private String mobile;
    private String nic;
    private String password;
    private LocalDateTime deletedAt;
    private String disabledReason;
    private String name;
    private String addressNo;
    private String addressStreet;
    private String city;
    private String dob;
    private String profilePic;
    private LocalDateTime PasswordUpdatedAt;
    private String otp;
    private int loginAttempts;
}
