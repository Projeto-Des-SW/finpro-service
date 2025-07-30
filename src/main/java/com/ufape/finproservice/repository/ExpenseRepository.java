package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Listar todas as despesas de um usuário
    List<Expense> findByUserId(Long userId);
}

