package com.ufape.finproservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufape.finproservice.model.ExpenseCategory;

import java.util.Optional;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
    Optional<ExpenseCategory> findByName(String name);
}
