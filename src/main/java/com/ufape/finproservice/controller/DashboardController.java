package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.CategoryExpenseSumDTO;
import com.ufape.finproservice.dto.MonthlyExpenseSumDTO;
import com.ufape.finproservice.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/expenses/monthly")
    public List<MonthlyExpenseSumDTO> getMonthlyExpenses(
            @RequestParam(required = false) Integer year
    ) {
        return dashboardService.getMonthlyExpenseSums(year);
    }

    @GetMapping("/expenses/by-category")
    public List<CategoryExpenseSumDTO> getExpensesByCategory(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        return dashboardService.getCategoryExpenseSums(year, month);
    }
}
