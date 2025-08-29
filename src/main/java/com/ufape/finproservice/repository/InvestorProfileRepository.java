package com.ufape.finproservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ufape.finproservice.model.InvestorProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvestorProfileRepository extends JpaRepository<InvestorProfile, Long> {
    Optional<InvestorProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    
    @Query("SELECT ip FROM InvestorProfile ip WHERE ip.user.id = :userId")
    Optional<InvestorProfile> findProfileByUserId(@Param("userId") Long userId);
}
