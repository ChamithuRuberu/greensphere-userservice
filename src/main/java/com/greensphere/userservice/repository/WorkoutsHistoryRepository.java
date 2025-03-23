package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.WorkoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutsHistoryRepository extends JpaRepository<WorkoutHistory,Long> {
    Optional<WorkoutHistory> findByHistoryId(String historyId);
}
