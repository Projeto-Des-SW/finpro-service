package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    
    List<IncomeEntity> findByUserId(Long userId);
    
    List<IncomeEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<IncomeEntity> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    List<IncomeEntity> findByPaymentOrigin(String paymentOrigin);
    
    List<IncomeEntity> findByUserIdAndPaymentOrigin(Long userId, String paymentOrigin);
    
    // Quando descomentar a categoria na entidade:
    // List<IncomeEntity> findByIncomeCategoryId(Long categoryId);
    // List<IncomeEntity> findByUserIdAndIncomeCategoryId(Long userId, Long categoryId);
}