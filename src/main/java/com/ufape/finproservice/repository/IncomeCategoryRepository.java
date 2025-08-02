package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    Optional<IncomeCategory> findByName(String name);

    List<IncomeCategory> findByNameContainingIgnoreCase(String name);
}

