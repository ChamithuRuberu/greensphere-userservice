package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.AdminIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminIncomeRepository extends JpaRepository<AdminIncome, Long> {

}
