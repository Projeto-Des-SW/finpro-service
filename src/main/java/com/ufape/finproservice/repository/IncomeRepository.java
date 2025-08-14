package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long>, JpaSpecificationExecutor<Income> {

    List<Income> findByUserId(Long userId);
    
    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Income> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    List<Income> findByPaymentOrigin(String paymentOrigin);
    
    List<Income> findByUserIdAndPaymentOrigin(Long userId, String paymentOrigin);

    List<Income> findByIncomeCategoryId(Long incomeCategoryId);

    List<Income> findByUserIdAndIncomeCategoryId(Long userId, Long incomeCategoryId);

}