package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.WorkOuts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface WorkoutsRepository extends JpaRepository<WorkOuts,Long> {

}
