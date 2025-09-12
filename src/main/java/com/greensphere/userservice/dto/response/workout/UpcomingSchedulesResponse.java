package com.greensphere.userservice.dto.response.workout;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UpcomingSchedulesResponse {
    // key: userId, value: list of upcoming schedules for that user
    private Map<String, List<UpcomingScheduleDTO>> userSchedules;
}


