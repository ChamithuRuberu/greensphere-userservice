package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.GymIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GymIncomeRepository extends JpaRepository<GymIncome, Long> {
    List<GymIncome> findByNextPaymentDateAfter(LocalDate date);
    List<GymIncome> findByGymIdAndNextPaymentDateAfter(Long gymId, LocalDate date);
}
