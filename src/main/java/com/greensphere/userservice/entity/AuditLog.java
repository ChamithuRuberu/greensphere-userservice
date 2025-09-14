package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actorId;      // username, govId, or system
    private String actorType;    // ROLE_USER, ROLE_TRAINER, ROLE_GYM, ROLE_SUPER_ADMIN
    private String action;       // HTTP_METHOD + PATH or business keyword
    private String resourceId;   // domain identifier, if any
    private String resourceType; // WORKOUT, PAYMENT, USER, etc.
    private String description;  // free text

    private LocalDateTime occurredAt;
    private String ip;
}


