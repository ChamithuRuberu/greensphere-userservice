package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.WorkoutProgressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutProgressLogRepository extends JpaRepository<WorkoutProgressLog, Long> {
    java.util.List<WorkoutProgressLog> findByWorkoutHistory_TrainerIdAndWorkoutDateAfter(String trainerId, java.time.LocalDateTime after);
    java.util.List<WorkoutProgressLog> findByWorkoutHistory_UserIdAndWorkoutDateAfter(String userId, java.time.LocalDateTime after);
    java.util.List<WorkoutProgressLog> findByWorkoutDateAfter(java.time.LocalDateTime after);
}