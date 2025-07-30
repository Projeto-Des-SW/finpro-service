package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    
    List<Income> findByUserId(Long userId);
    
    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Income> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    List<Income> findByPaymentOrigin(String paymentOrigin);
    
    List<Income> findByUserIdAndPaymentOrigin(Long userId, String paymentOrigin);
    
    // Quando descomentar a categoria na entidade:
    // List<IncomeEntity> findByIncomeCategoryId(Long categoryId);
    // List<IncomeEntity> findByUserIdAndIncomeCategoryId(Long userId, Long categoryId);
}