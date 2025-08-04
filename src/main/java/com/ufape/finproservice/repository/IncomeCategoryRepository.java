package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    Optional<IncomeCategory> findByName(String name);

}

