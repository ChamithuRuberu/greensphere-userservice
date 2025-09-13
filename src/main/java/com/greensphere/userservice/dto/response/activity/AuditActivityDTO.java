package com.greensphere.userservice.dto.response.activity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditActivityDTO {
    private String actorType; // USER, TRAINER, GYM, ADMIN
    private String actorId;
    private String actorName;
    private String activityType; // WORKOUT_LOG, TRAINER_PAYMENT, GYM_PAYMENT, OTHER
    private String description;
    private LocalDateTime occurredAt;
}
