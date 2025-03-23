package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.WorkoutProgressLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutProgressLogRepository extends JpaRepository<WorkoutProgressLog, Long> {
}