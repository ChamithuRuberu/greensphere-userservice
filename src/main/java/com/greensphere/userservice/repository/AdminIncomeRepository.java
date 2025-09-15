package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.AdminIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AdminIncomeRepository extends JpaRepository<AdminIncome, Long> {

    @Query("select coalesce(sum(a.amount), 0) from AdminIncome a")
    BigDecimal sumAllAmounts();
}
