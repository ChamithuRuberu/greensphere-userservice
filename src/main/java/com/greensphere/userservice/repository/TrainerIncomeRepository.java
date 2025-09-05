package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.TrainerIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerIncomeRepository extends JpaRepository<TrainerIncome, Long> {
}
