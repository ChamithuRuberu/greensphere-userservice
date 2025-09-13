package com.greensphere.userservice.dto.response.workout;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecentActivityDTO {
    private String userId;
    private String userName;
    private String exerciseName;
    private Integer completedSets;
    private Integer completedReps;
    private String weightUsed;
    private Integer performanceRating;
    private String energyLevel;
    private String trainerNotes;
    private String clientFeedback;
    private LocalDateTime workoutDate;
    private Integer weekNumber;
    private String day;
}
