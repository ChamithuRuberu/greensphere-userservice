package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {

    boolean existsByToken(String token);
}
