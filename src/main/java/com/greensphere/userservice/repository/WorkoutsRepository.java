package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.WorkOuts;
import com.greensphere.userservice.entity.WorkoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface WorkoutsRepository extends JpaRepository<WorkOuts,Long> {

    List<WorkOuts> findWorkOutsById(Long clientId);
    Optional<WorkOuts> findByWorkoutHistoryAndNameAndWeekNumber(WorkoutHistory workoutHistory, String name, Integer weekNumber);
}
