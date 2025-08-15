package com.ufape.finproservice.repository;

import com.ufape.finproservice.dto.MonthlyIncomeSumDTO;
import com.ufape.finproservice.model.Income;
import com.ufape.finproservice.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        select new com.ufape.finproservice.dto.MonthlyIncomeSumDTO(
            year(i.date), month(i.date), sum(i.amount
        )
        from Income i
        where i.user = :user
          and (:year is null or year(i.date) = :year)
        group by year(i.date), month(i.date)
        order by year(i.date), month(i.date)
        """)
    List<MonthlyIncomeSumDTO> sumByMonth(
            @Param("user") UserEntity user,
            @Param("year") Integer year
    );   
}