package com.greensphere.userservice.dto.request.workout;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateWorkoutRequest {

    private String clientId;
    private String workoutName;
    private String startDate;
    private String endDate;
    private List<WorkOuts> workouts;

    @Getter
    @Setter
    public static class WorkOuts {
        private String type;
        private String day;
        private String startTime;
        private int duration;
        private List<Exercise> exercises;
        private String notes;

        @Getter
        @Setter
        public static class Exercise {
            private String name;
            private int sets;
            private int reps;
            private String weight;
            private String notes;
        }
    }
}
