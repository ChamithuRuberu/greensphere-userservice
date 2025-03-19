package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Gym,Long> {

    Gym findGymByEmail(String id);
}
