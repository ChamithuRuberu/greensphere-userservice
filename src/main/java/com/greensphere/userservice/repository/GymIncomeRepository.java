package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.GymIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymIncomeRepository extends JpaRepository<GymIncome, Long> {

}
