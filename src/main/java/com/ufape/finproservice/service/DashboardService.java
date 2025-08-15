package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.CategoryExpenseSumDTO;
import com.ufape.finproservice.dto.CategoryIncomeSumDTO;
import com.ufape.finproservice.dto.MonthlyExpenseSumDTO;
import com.ufape.finproservice.dto.MonthlyIncomeSumDTO;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.ExpenseRepository;
import com.ufape.finproservice.repository.IncomeRepository;
import com.ufape.finproservice.util.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final CurrentUserService currentUserService;

    public List<MonthlyExpenseSumDTO> getMonthlyExpenseSums(Integer year) {
        UserEntity user = currentUserService.getCurrentUser();
        return expenseRepository.sumByMonth(user, year);
    }

    public List<CategoryExpenseSumDTO> getCategoryExpenseSums(Integer year, Integer month) {
        UserEntity user = currentUserService.getCurrentUser();
        return expenseRepository.sumByCategoryPerMonth(user, year, month);
    }

    public List<MonthlyIncomeSumDTO> getMonthlyIncomeSums(Integer year) {
        UserEntity user = currentUserService.getCurrentUser();
        return incomeRepository.sumByMonth(user, year);
    }

    public List<CategoryIncomeSumDTO> getCategoryIncomeSums(Integer year, Integer month) {
        UserEntity user = currentUserService.getCurrentUser();
        return incomeRepository.sumByCategoryPerMonth(user, year, month);
    }    
}
