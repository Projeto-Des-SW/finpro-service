package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    // Listar todas as despesas de um usuário
    List<ExpenseEntity> findByUserId(Long userId);
}

