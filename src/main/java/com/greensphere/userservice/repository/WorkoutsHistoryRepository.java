package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.WorkoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutsHistoryRepository extends JpaRepository<WorkoutHistory,Long> {
}
