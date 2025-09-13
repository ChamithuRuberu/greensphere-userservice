package com.greensphere.userservice.dto.response.workout;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RecentActivitiesResponse {
    private Map<String, List<RecentActivityDTO>> userActivities;
}
