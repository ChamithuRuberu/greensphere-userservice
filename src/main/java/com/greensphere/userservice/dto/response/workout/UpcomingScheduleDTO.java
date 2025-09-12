package com.greensphere.userservice.dto.response.workout;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpcomingScheduleDTO {
    private String userId;
    private String workoutName;
    private String day;
    private String startTime;
    private String endTime;
    private Integer weekNumber;
    private String status;
}


