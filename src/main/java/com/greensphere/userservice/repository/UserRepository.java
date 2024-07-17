package com.greensphere.userservice.repository;

import com.greensphere.userservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findAppUserByUsername(String username);

    AppUser findAppUserByEmail(String email);

    @Query("SELECT a FROM AppUser a WHERE a.status = :status and a.nic IN :nics")
    List<AppUser> findAllByNicIn(@Param("status") String status, @Param("nics") List<String> nics);

    boolean existsAppUserByMobileAndStatus(String mobile, String status);

    List<AppUser> findAppUsersByNicOrMobileOrEmail(String nic, String mobile, String email);

}
