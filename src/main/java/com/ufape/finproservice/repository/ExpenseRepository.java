package com.ufape.finproservice.repository;

import com.ufape.finproservice.dto.CategoryExpenseSumDTO;
import com.ufape.finproservice.dto.MonthlyExpenseSumDTO;
import com.ufape.finproservice.model.Expense;
import com.ufape.finproservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    List<Expense> findByUserId(Long userId);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<Expense> findByPaymentDestination(String paymentOrigin);

    List<Expense> findByUserIdAndPaymentDestination(Long userId, String paymentDestination);

    List<Expense> findByExpenseCategoryId(Long categoryId);
    
    List<Expense> findByUserIdAndExpenseCategoryId(Long userId, Long categoryId);

    @Query("""
        select new com.ufape.finproservice.dto.MonthlyExpenseSumDTO(
            year(e.date), month(e.date), sum(e.amount)
        )
        from Expense e
        where e.user = :user
          and (:year is null or year(e.date) = :year)
        group by year(e.date), month(e.date)
        order by year(e.date), month(e.date)
        """)
    List<MonthlyExpenseSumDTO> sumByMonth(
            @Param("user") UserEntity user,
            @Param("year") Integer year
    );

    @Query("""
    select new com.ufape.finproservice.dto.CategoryExpenseSumDTO(
        cast(extract(year  from e.date) as int),
        cast(extract(month from e.date) as int),
        ec.id,
        ec.name,
        sum(e.amount)
    )
    from Expense e
    join e.expenseCategory ec
    where e.user = :user
      and (:year  is null or extract(year  from e.date) = :year)
      and (:month is null or extract(month from e.date) = :month)
    group by extract(year from e.date), extract(month from e.date), ec.id, ec.name
    order by extract(year from e.date), extract(month from e.date), ec.name
    """)
    List<CategoryExpenseSumDTO> sumByCategoryPerMonth(
            @Param("user") UserEntity user,
            @Param("year") Integer year,
            @Param("month") Integer month
    );
}