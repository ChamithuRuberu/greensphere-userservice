package com.greensphere.userservice.dto.response.workout;

import com.greensphere.userservice.entity.WorkOuts;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllWorkoutsResponse {
    private List<WorkOuts> workouts;
}
