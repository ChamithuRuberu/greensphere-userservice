package com.greensphere.userservice.dto.request.workout;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetWorkoutsRequest {

    private String workoutId;
    private String trainId;
}
