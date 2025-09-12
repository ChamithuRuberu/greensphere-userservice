package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.TrainerIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainerIncomeRepository extends JpaRepository<TrainerIncome, Long> {
    // Upcoming after today (per-trainer / all)
    List<TrainerIncome> findByTrainerIdAndNextPaymentDateAfter(Long trainerId, LocalDate date);
    List<TrainerIncome> findByNextPaymentDateAfter(LocalDate date);

    // Due soon between [today, today+days] (per-trainer / all)
    List<TrainerIncome> findByTrainerIdAndNextPaymentDateBetween(Long trainerId, LocalDate from, LocalDate to);
    List<TrainerIncome> findByNextPaymentDateBetween(LocalDate from, LocalDate to);

    // Optional: Overdue (before today) if you ever need it
    List<TrainerIncome> findByTrainerIdAndNextPaymentDateBefore(Long trainerId, LocalDate date);
    List<TrainerIncome> findByNextPaymentDateBefore(LocalDate date);

}
